package qapital.transactions.dao;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlite3.SQLitePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qapital.transactions.domain.Transaction;
import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

public class TransactionDaoImpl implements TransactionDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionDaoImpl.class);

    private Jdbi jdbi;

    public TransactionDaoImpl(DataSource dataSource) {
        this.jdbi = Objects.requireNonNull(Jdbi.create(dataSource)
                .installPlugin(new SQLitePlugin())
                .installPlugin(new SqlObjectPlugin()), "dataSource");
    }

    @Override
    public List<Transaction> getTransactions(Long userId) {
        List<Transaction> transactions = jdbi.withExtension(TransactionDao.class, dao -> dao.getTransactions(userId));
        LOGGER.info("Successfully fetched {} transaction(s) for userId: {}", transactions.size(), userId);
        return transactions;
    }

    @Override
    public Transaction getTransaction(Long userId, Long transactionId) {
        Transaction transaction = jdbi.withExtension(TransactionDao.class, dao -> dao.getTransaction(userId, transactionId));
        LOGGER.info("Successfully fetched {} transaction: {} for userId: {}", transaction.getId(), transaction.getUserId());
        return transaction;
    }

    @Override
    public List<Transaction> getTransactions() {
        List<Transaction> transactions =  jdbi.withExtension(TransactionDao.class, TransactionDao::getTransactions);
        LOGGER.info("Successfully fetched {} transaction(s)", transactions.size());
        return transactions;
    }

    @Override
    public void storeTransaction(Transaction transaction) {
        persistTransactions(transaction);
    }

    private void persistTransactions(Transaction transaction){
        LOGGER.info("Storing transaction {} for userId {}", transaction.getId(), transaction.getUserId());
        jdbi.open().attach(TransactionDao.class).storeTransaction(transaction);
    }
}