package qapital.transactions.service;

import org.springframework.stereotype.Service;
import qapital.transactions.domain.Transaction;
import java.util.List;
import static java.util.Objects.isNull;
import static qapital.transactions.service.DummyTransactionsGenerator.createDummyTransactions;

@Service
public class StandardTransactionsServiceImpl implements TransactionsService {

    @Override
    public List<Transaction> getLatestTransactionsForUser(Long userId) {
      return !isNull(userId) ?  createDummyTransactions(userId) : null;
    }
}
