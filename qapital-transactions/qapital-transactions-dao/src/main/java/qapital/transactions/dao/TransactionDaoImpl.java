package qapital.transactions.dao;

import com.google.common.collect.Lists;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qapital.transactions.domain.Transaction;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.isNull;

public class TransactionDaoImpl implements TransactionDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionDaoImpl.class);

    private Jdbi jdbi;

    public TransactionDaoImpl(Jdbi jdbi) {
        this.jdbi = Objects.requireNonNull(jdbi, "jdbi");
    }

    @Override
    public List<Transaction> getTransactions(Long userId) {
        List<Transaction> transactions = Lists.newArrayList();
        if (!isNull(userId)) {
            transactions = jdbi.withExtension(TransactionDao.class, dao -> dao.getTransactions(userId));
            LOGGER.info("Successfully fetched {} transactions for userId: {}", transactions.size(), userId);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getTransactions() {
        List<Transaction> transactions =  jdbi.withExtension(TransactionDao.class, TransactionDao::getTransactions);
        LOGGER.info("Successfully fetched {} transactions", transactions.size());
        return transactions;
    }

    @Override
    public void storeTransaction(Transaction transaction) {
        jdbi.useExtension(TransactionDao.class, dao -> storeTransaction(transaction));
    }
}