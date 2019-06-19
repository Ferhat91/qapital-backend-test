package qapital.transactions.service;


import qapital.transactions.domain.Transaction;
import java.util.List;

public interface TransactionsService {

    List<Transaction> getLatestTransactionsForUser(Long userId);
}
