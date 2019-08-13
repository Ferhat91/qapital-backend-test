package qapital.transactions.service;

import qapital.transactions.dao.TransactionDao;
import qapital.transactions.domain.Transaction;

import java.util.List;
import java.util.Objects;

public class TransactionServiceImpl implements TransactionsService {

    private TransactionDao transactionDao;

    public TransactionServiceImpl(TransactionDao transactionDao) {
        this.transactionDao = Objects.requireNonNull(transactionDao, "transactionDao");
    }

    @Override
    public List<Transaction> getTransactions(Long userId) {
        List<Transaction> transactions = transactionDao.getTransactions(userId);
        return transactions;
    }

    @Override
    public List<Transaction> getTransactions() {
        List<Transaction> transactions = transactionDao.getTransactions();
        return transactions;
    }

    @Override
    public void storeTransaction(Transaction transaction) {
        transactionDao.storeTransaction(transaction);
    }
}
