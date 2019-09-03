package qapital.savings.service.event;

import qapital.broker.kafka.event.serialization.Mapper;
import qapital.broker.kafka.event.serialization.ValueMapper;
import qapital.savings.domain.Transaction;
import static java.util.Objects.isNull;
import com.qapital.transaction.event.Transaction.TransactionCreatedEvent;
import qapital.savings.domain.TransactionType;

public class TransactionMapper {

    public static Transaction map(TransactionCreatedEvent transactionCreatedEvent) {
        Transaction transaction = null;
        if (!isNull(transactionCreatedEvent)) {
            transaction = Mapper.of(transactionCreatedEvent, Transaction::builder)
                    .mapIfFalseNull(transactionCreatedEvent::hasId, TransactionCreatedEvent::getId, ValueMapper::map, Transaction.Builder::withId)
                    .mapIfFalseNull(transactionCreatedEvent::hasUserId, TransactionCreatedEvent::getUserId, ValueMapper::map, Transaction.Builder::withUserId)
                    .mapIfFalseNull(transactionCreatedEvent::hasAmount, TransactionCreatedEvent::getAmount, ValueMapper::map, Transaction.Builder::withAmount)
                    .mapIfFalseNull(transactionCreatedEvent::hasDescription, TransactionCreatedEvent::getDescription, ValueMapper::map, Transaction.Builder::withDescription)
                    .mapIfFalseNull(transactionCreatedEvent::hasExecutionTime, TransactionCreatedEvent::getExecutionTime, ValueMapper::map, Transaction.Builder::withExecutionTime)
                    .map(TransactionCreatedEvent::getType, TransactionMapper::map, Transaction.Builder::withTransactionType)
                    .build(Transaction.Builder::build);
        }
        return transaction;
    }

    private static TransactionType map(com.qapital.transaction.event.Transaction.Type transactionType) {
        TransactionType type = null;
        if (!isNull(transactionType)) {
            switch (transactionType) {
                case MANUAL:
                    return TransactionType.MANUAL;
                case STARTED:
                    return TransactionType.STARTED;
                case STOPPED:
                    return TransactionType.STOPPED;
                case RULE_APPLICATION:
                    return TransactionType.RULE_APPLICATION;
                case IFTTT_TRANSFER:
                    return TransactionType.IFTTT_TRANSFER;
                case JOINED:
                    return TransactionType.JOINED;
                case WITHDRAWL:
                    return TransactionType.WITHDRAWL;
                case INTERNAL_TRANSFER:
                    return TransactionType.INTERNAL_TRANSFER;
                case CANCELLATION:
                    return TransactionType.CANCELLATION;
                case INCENTIVE_PAYOUT:
                    return TransactionType.INCENTIVE_PAYOUT;
                case INTEREST:
                    return TransactionType.INTEREST;
                default:
                    return TransactionType.UNKNOWN;
            }
        }
        return TransactionType.UNKNOWN;
    }
}
