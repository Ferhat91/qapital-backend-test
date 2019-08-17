package qapital.transactions.service;


import qapital.transactions.domain.Transaction;
import java.util.List;

public interface TransactionsService {

    List<Transaction> getTransactions(Long userId);

    List<Transaction> getTransactions();

    void storeTransaction(Transaction transaction);

}
