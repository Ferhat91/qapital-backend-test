package qapital.savings.service.event;

import com.qapital.broker.kafka.event.EventWrapperOuterClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import qapital.broker.kafka.event.serialization.ProtobufSerializer;
import qapital.broker.kafka.event.serialization.Serializer;
import qapital.broker.kafka.event.subscriber.ConsumerListener;
import qapital.savings.dao.event.SavingsEventDao;
import qapital.savings.dao.rule.SavingsRuleDao;
import qapital.savings.dao.transfer.SavingsTransferDao;
import qapital.savings.domain.Transaction;
import qapital.savings.domain.event.SavingsEvent;
import qapital.savings.domain.rule.SavingsRule;
import qapital.savings.service.transfer.CreateSavingsTransfer;
import qapital.savings.service.utility.IdGenerator;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static qapital.savings.service.event.CreateSavingsEvent.createSavingsEvent;

public class TransactionConsumerListenerImpl implements ConsumerListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionConsumerListenerImpl.class);

    private Serializer serializer = new ProtobufSerializer();
    private SavingsRuleDao savingsRuleDao;
    private SavingsEventDao savingsEventDao;
    private SavingsTransferDao savingsTransferDao;
    private IdGenerator savingsIdGenerator;

    public TransactionConsumerListenerImpl(SavingsRuleDao savingsRuleDao,
                                           SavingsEventDao savingsEventDao,
                                           SavingsTransferDao savingsTransferDao,
                                           IdGenerator savingsIdGenerator) {
        this.savingsRuleDao = Objects.requireNonNull(savingsRuleDao, "savingsRuleDao");
        this.savingsEventDao = Objects.requireNonNull(savingsEventDao, "savingsEvent");
        this.savingsTransferDao = Objects.requireNonNull(savingsTransferDao, "savingsTransferDao");
        this.savingsIdGenerator = Objects.requireNonNull(savingsIdGenerator, "savingsIdGenerator");
    }

    @Transactional
    @Override
    public void onNext(EventWrapperOuterClass.EventWrapper message) {

        com.qapital.transaction.event.Transaction.TransactionCreatedEvent transactionEvent = serializer.deserialize(message.getType(), message.getData().toByteArray());
        Transaction transaction = TransactionMapper.map(transactionEvent);
        // We should fetch the transaction by the subscribed transactionId from the message and call the API of transaction-service (not the DB!)
        LOGGER.info("transaction: {} was subscribed at {} for user: {}", transaction.getId(), Instant.now(), transaction.getUserId());
        List<SavingsRule> savingsRules = savingsRuleDao.getSavingsRules(transaction.getUserId());

        savingsRules.forEach(savingsRule -> {
            Optional<SavingsEvent> createdSavingsEvent = createSavingsEvent(savingsIdGenerator.generateLong(), savingsRule, transaction);

            createdSavingsEvent.ifPresent(savingsEvent -> savingsEventDao.persistSavingsEvent(savingsEvent));
            createdSavingsEvent.ifPresent(savingsEvent ->
                    CreateSavingsTransfer.createSavingsTransfer(savingsEvent).ifPresent(savingsTransfer -> savingsTransferDao.persistSavings(savingsTransfer)));
        });
    }

    @Override
    public void onError(Throwable error) {
        LOGGER.error("Received unexpected error from consumer", error);
    }

    @Override
    public void onClose() {
    }
}
