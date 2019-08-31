package qapital.savings.service.mapper;

import qapital.savings.domain.Transaction;
import static java.util.Objects.isNull;
import com.qapital.transaction.event.Transaction.TransactionCreatedEvent;

public class TransactionMapper {

    public static Transaction map(TransactionCreatedEvent transactionCreatedEvent){
        Transaction transaction = null;
        if (!isNull(transactionCreatedEvent)){
            transaction = Mapper.of(transactionCreatedEvent, Transaction::builder)
                    .mapIfFalseNull(transactionCreatedEvent::hasId, TransactionCreatedEvent::getId,ValueMapper::map,Transaction.Builder::withId)
                    .mapIfFalseNull(transactionCreatedEvent::hasUserId, TransactionCreatedEvent::getUserId,ValueMapper::map ,Transaction.Builder::withUserId)
                    .mapIfFalseNull(transactionCreatedEvent::hasAmount, TransactionCreatedEvent::getAmount, ValueMapper::map, Transaction.Builder::withAmount)
                    .mapIfFalseNull(transactionCreatedEvent::hasDescription, TransactionCreatedEvent::getDescription, ValueMapper::map, Transaction.Builder::withDescription)
                    .mapIfFalseNull(transactionCreatedEvent::hasExecutionTime, TransactionCreatedEvent::getExecutionTime, ValueMapper::map, Transaction.Builder::withExecutionTime)
                    .build(Transaction.Builder::build);
        }
        return transaction;
    }
}
