package qapital.savings.service.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qapital.broker.kafka.event.serialization.Mapper;
import qapital.savings.domain.Transaction;
import qapital.savings.domain.TransactionType;
import qapital.savings.domain.event.EventType;
import qapital.savings.domain.event.SavingsEvent;
import qapital.savings.domain.rule.SavingsRule;
import java.time.LocalDate;
import java.util.Optional;
import static java.util.Objects.isNull;

public class CreateSavingsEvent {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateSavingsEvent.class);

    public static Optional<SavingsEvent> createSavingsEvent(Long generatedId,SavingsRule savingsRule, Transaction transaction) {

        if(isSavingsRuleApplicable(savingsRule, transaction)){
            SavingsEvent savingsEvent = Mapper.of(transaction, SavingsEvent::builder)
                    .map(savingsEventId -> generatedId, SavingsEvent.Builder::withId)
                    .map(savingsTransferId -> generatedId, SavingsEvent.Builder::withSavingTransferId)
                    .map(Transaction::getAmount, transactionAmount -> manageAmountToSave(savingsRule, transaction), SavingsEvent.Builder::withAmount)
                    .map(Transaction::getExecutionTime, SavingsEvent.Builder::withDate)
                    .map(Transaction::getUserId, SavingsEvent.Builder::withUserId)
                    .map(Transaction::getId, SavingsEvent.Builder::withTriggerId)
                    .map(transactionType -> EventType.valueOf(transaction.getTransactionType().getTransactionTypeValue()) , SavingsEvent.Builder::withEventType)
                    .map(ruleType -> savingsRule.getRuleType(), SavingsEvent.Builder::withRuleType)
                    .map(savingRuleId -> savingsRule.getId(), SavingsEvent.Builder::withSavingsRuleId)
                    .map(created -> LocalDate.now().toString(), SavingsEvent.Builder::withCreated)
                    .map(savingsGoalId -> savingsRule.getSavingsGoalId(), SavingsEvent.Builder::withSavingsGoaldId)
                    .build(SavingsEvent.Builder::build);
            return Optional.of(savingsEvent);
        }
        return Optional.empty();
    }

    private static Boolean isSavingsRuleApplicable(SavingsRule savingsRule, Transaction transaction){
        if(!isNull(transaction) && !isNull(savingsRule) && !transaction.getDescription().equals("Salary")
                && !isNull(transaction.getTransactionType()) && transaction.getTransactionType().equals(TransactionType.RULE_APPLICATION)){
            LOGGER.info("Transaction status {} are applicable on savingsRule {} " , transaction.getTransactionType(), savingsRule.getId());
            return true;
        }
        LOGGER.info("Transaction status {} are NOT applicable on savingsRule {} " , transaction.getTransactionType(), savingsRule.getId());
        return false;
    }

    private static Double manageAmountToSave(SavingsRule savingsRule, Transaction transaction) {
        switch (savingsRule.getRuleType()){
            case ROUND_UP:
                return getAmountToSaveRoundUpRule(savingsRule.getAmount(), transaction.getAmount());
            case GUILTY_PLEASURE:
                return getAmountToSaveGuiltyPleasureRule(savingsRule, transaction);
            default:
                throw new UnsupportedOperationException();
        }
    }

    private static Double getAmountToSaveRoundUpRule(Double savingsRuleAmount, Double transactionAmount) {
        if(!isNull(savingsRuleAmount) && !isNull(transactionAmount)) {
            Double remainder = transactionAmount % savingsRuleAmount;
            Double savedAmount = savingsRuleAmount - remainder;
            return savedAmount;
        }
        return null;
    }

    private static Double getAmountToSaveGuiltyPleasureRule(SavingsRule savingsRule, Transaction transaction) {
        if (!isNull(savingsRule) && !isNull(transaction)) {
            return savingsRule.getDescription().equals(transaction.getDescription()) ? savingsRule.getAmount() : null;
        }
        return null;
    }
}
