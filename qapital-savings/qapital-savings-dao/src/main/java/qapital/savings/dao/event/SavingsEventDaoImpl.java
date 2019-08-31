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

public class SavingsDaoImpl implements SavingsEventDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SavingsDaoImpl.class);

    private Jdbi jdbi;

    public SavingsDaoImpl(DataSource dataSource) {
        this.jdbi = Objects.requireNonNull(Jdbi.create(dataSource)
                .installPlugin(new SQLitePlugin())
                .installPlugin(new SqlObjectPlugin()), "dataSource");
    }

    @Override
    public List<SavingsEvent> getSavingsEvents(Long userId) {
        return null;
    }

    @Override
    public void persistSavingsEvent(SavingsEvent savingsEvent) {

    }
}
