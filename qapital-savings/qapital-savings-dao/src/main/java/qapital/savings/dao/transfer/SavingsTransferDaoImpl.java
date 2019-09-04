package qapital.savings.dao.transfer;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlite3.SQLitePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qapital.savings.domain.transfer.SavingsTransfer;
import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

public class SavingsTransferDaoImpl implements SavingsTransferDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SavingsTransferDaoImpl.class);

    private Jdbi jdbi;

    public SavingsTransferDaoImpl(DataSource dataSource) {
        this.jdbi = Objects.requireNonNull(Jdbi.create(dataSource)
                .installPlugin(new SQLitePlugin())
                .installPlugin(new SqlObjectPlugin()), "dataSource");
    }

    @Override
    public void persistSavings(SavingsTransfer savingsTransfer) {
        LOGGER.info("Storing savingsTransfer {} for userId {}", savingsTransfer.getId(), savingsTransfer.getUserId());
        jdbi.open().attach(SavingsTransferDao.class).persistSavings(savingsTransfer);
    }

    @Override
    public List<SavingsTransfer> getSavingsTransfers(Long userId) {
        List<SavingsTransfer> savingsTransfers = jdbi.withExtension(SavingsTransferDao.class, dao -> dao.getSavingsTransfers(userId));
        LOGGER.info("Successfully fetched {} savingTransfer(s) for userId: {}", savingsTransfers.size(), userId);
        return savingsTransfers;
    }

    @Override
    public SavingsTransfer getSavingsTransfer(Long id, Long userId) {
        SavingsTransfer savingsTransfer = jdbi.withExtension(SavingsTransferDao.class, dao -> dao.getSavingsTransfer(id, userId));
        LOGGER.info("Successfully fetched savingTransfer {} for userId: {}", id, userId);
        return savingsTransfer;
    }
}
