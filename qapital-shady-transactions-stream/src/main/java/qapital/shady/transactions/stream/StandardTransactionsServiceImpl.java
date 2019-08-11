package qapital.shady.transactions.stream;

import org.springframework.stereotype.Service;
import qapital.transactions.domain.Transaction;
import qapital.transactions.service.TransactionsService;

import java.util.List;
import static java.util.Objects.isNull;
import static qapital.shady.transactions.stream.DummyTransactionsGenerator.createDummyTransactions;

@Service
public class StandardTransactionsServiceImpl implements TransactionsService {

    @Override
    public List<Transaction> getLatestTransactionsForUser(Long userId) {
      return !isNull(userId) ?  createDummyTransactions(userId) : null;
    }
}
