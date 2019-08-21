package qapital.transactions.service;

import qapital.broker.kafka.event.publisher.EventPublisher;
import org.springframework.transaction.annotation.Transactional;
import qapital.transactions.dao.TransactionDao;
import qapital.transactions.domain.Transaction;
import java.util.List;
import java.util.Objects;

public class TransactionServiceImpl implements TransactionsService {

    private TransactionDao transactionDao;

    private EventPublisher eventPublisher;

    public TransactionServiceImpl(TransactionDao transactionDao, EventPublisher eventPublisher) {
        this.transactionDao = Objects.requireNonNull(transactionDao, "transactionDao");
        this.eventPublisher = Objects.requireNonNull(eventPublisher,"eventPublisher");
    }

    @Override
    @Transactional
    public void storeTransaction(Transaction transaction) {
        transactionDao.storeTransaction(transaction);
        eventPublisher.publish(com.qapital.transaction.event.Transaction
                .TransactionCreatedEvent.newBuilder().setId(mapLong(transaction.getId()))
                .build());
    }

    @Override
    public List<Transaction> getTransactions(Long userId) {
        List<Transaction> transactions = transactionDao.getTransactions(userId);
        return transactions;
    }

    @Override
    public Transaction getTransaction(Long userId, Long transactionId) {
        Transaction transaction = transactionDao.getTransaction(userId,transactionId);
        return transaction;
    }

    @Override
    public List<Transaction> getTransactions() {
        List<Transaction> transactions = transactionDao.getTransactions();
        return transactions;
    }

    private com.google.protobuf.Int64Value mapLong(Long longValue){
        return com.google.protobuf.Int64Value.newBuilder().setValue(longValue).build();
    }
}
