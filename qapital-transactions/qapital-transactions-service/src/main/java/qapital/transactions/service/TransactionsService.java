package qapital.transactions.service;

import qapital.transactions.domain.Transaction;
import java.util.List;

public interface TransactionsService {

    List<Transaction> getTransactions(Long userId);

    Transaction getTransaction(Long userId, Long transactionId);

    List<Transaction> getTransactions();

    void persistTransaction(Transaction transaction);
}
