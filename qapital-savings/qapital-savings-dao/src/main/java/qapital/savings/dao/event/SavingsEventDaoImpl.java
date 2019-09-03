package qapital.savings.dao.event;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlite3.SQLitePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qapital.savings.domain.event.SavingsEvent;
import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

public class SavingsEventDaoImpl implements SavingsEventDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SavingsEventDaoImpl.class);

    private Jdbi jdbi;

    public SavingsEventDaoImpl(DataSource dataSource) {
        this.jdbi = Objects.requireNonNull(Jdbi.create(dataSource)
                .installPlugin(new SQLitePlugin())
                .installPlugin(new SqlObjectPlugin()), "dataSource");
    }

    @Override
    public List<SavingsEvent> getSavingsEvents(Long userId) {
        List<SavingsEvent> savingsEvents = jdbi.withExtension(SavingsEventDao.class, dao -> dao.getSavingsEvents(userId));
        LOGGER.info("Successfully fetched {} savingEvent(s) for user: {}", savingsEvents.size(), userId);
        return savingsEvents;
    }

    @Override
    public SavingsEvent getSavingsEvent(Long id, Long userId) {
        SavingsEvent savingsEvent = jdbi.withExtension(SavingsEventDao.class, dao -> dao.getSavingsEvent(id, userId));
        LOGGER.info("Successfully fetched savingEvent {} for user: {}", id, userId);
        return savingsEvent;
    }

    @Override
    public SavingsEvent getSavingsEventForTransaction(Long transactionId, Long userId) {
        SavingsEvent savingsEvent = jdbi.withExtension(SavingsEventDao.class, dao -> dao.getSavingsEventForTransaction(transactionId, userId));
        LOGGER.info("Successfully fetched savingEvent {} for user: {}", transactionId, userId);
        return savingsEvent;
    }

    @Override
    public void persistSavingsEvent(SavingsEvent savingsEvent) {
        LOGGER.info("Storing savingsEvent {} for user {}", savingsEvent.getId(), savingsEvent.getUserId());
        jdbi.open().attach(SavingsEventDao.class).persistSavingsEvent(savingsEvent);
    }
}
