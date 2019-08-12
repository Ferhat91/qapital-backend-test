package qapital.transactions.service;


import qapital.transactions.domain.Transaction;
import java.util.List;

public interface TransactionsService {

    List<Transaction> getTransactions(Long userId);

    void storeTransaction(Transaction transaction);
}
