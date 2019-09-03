package qapital.transactions.service;

import qapital.broker.kafka.event.serialization.Mapper;
import qapital.broker.kafka.event.serialization.ValueMapper;
import com.qapital.transaction.event.Transaction;
import qapital.transactions.domain.TransactionType;
import static java.util.Objects.isNull;

public class TransactionEventMapper {

    public static Transaction.TransactionCreatedEvent mapToTransactionCreatedEvent(qapital.transactions.domain.Transaction transaction) {
        if (!isNull(transaction)) {
            Transaction.TransactionCreatedEvent transactionCreatedEvent = Mapper.of(transaction, com.qapital.transaction.event.Transaction.TransactionCreatedEvent::newBuilder)
                    .mapIfNullThen(qapital.transactions.domain.Transaction::getId, ValueMapper::map, Transaction.TransactionCreatedEvent.Builder::setId, Transaction.TransactionCreatedEvent.Builder::clearId)
                    .mapIfNullThen(qapital.transactions.domain.Transaction::getUserId, ValueMapper::map, Transaction.TransactionCreatedEvent.Builder::setUserId, Transaction.TransactionCreatedEvent.Builder::clearUserId)
                    .mapIfNullThen(qapital.transactions.domain.Transaction::getDescription, ValueMapper::map, Transaction.TransactionCreatedEvent.Builder::setDescription, Transaction.TransactionCreatedEvent.Builder::clearDescription)
                    .mapIfNullThen(qapital.transactions.domain.Transaction::getAmount, ValueMapper::map, Transaction.TransactionCreatedEvent.Builder::setAmount, Transaction.TransactionCreatedEvent.Builder::clearAmount)
                    .mapIfNullThen(qapital.transactions.domain.Transaction::getExecutionTime, ValueMapper::map, Transaction.TransactionCreatedEvent.Builder::setExecutionTime, Transaction.TransactionCreatedEvent.Builder::clearExecutionTime)
                    .mapIfNullThen(qapital.transactions.domain.Transaction::getType, TransactionEventMapper::map, Transaction.TransactionCreatedEvent.Builder::setType, Transaction.TransactionCreatedEvent.Builder::clearType)
                    .build(Transaction.TransactionCreatedEvent.Builder::build);
            return transactionCreatedEvent;
        }
        return Transaction.TransactionCreatedEvent.newBuilder().clear().build();
    }

    private static Transaction.Type map(TransactionType transactionType) {
        if (!isNull(transactionType)) {
            switch (transactionType) {
                case MANUAL:
                    return Transaction.Type.MANUAL;
                case STARTED:
                    return Transaction.Type.STARTED;
                case STOPPED:
                    return Transaction.Type.STOPPED;
                case RULE_APPLICATION:
                    return Transaction.Type.RULE_APPLICATION;
                case IFTTT_TRANSFER:
                    return Transaction.Type.IFTTT_TRANSFER;
                case JOINED:
                    return Transaction.Type.JOINED;
                case WITHDRAWL:
                    return Transaction.Type.WITHDRAWL;
                case INTERNAL_TRANSFER:
                    return Transaction.Type.INTERNAL_TRANSFER;
                case CANCELLATION:
                    return Transaction.Type.CANCELLATION;
                case INCENTIVE_PAYOUT:
                    return Transaction.Type.INCENTIVE_PAYOUT;
                case INTEREST:
                    return Transaction.Type.INTEREST;
                default:
                    return Transaction.Type.UNKNOWN;
            }
        }
        return null;
    }
}