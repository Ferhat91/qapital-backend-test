package qapital.savings.service;

import qapital.savings.domain.Transaction;
import qapital.savings.domain.event.SavingsEvent;
import qapital.savings.domain.rule.SavingsRule;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import static java.util.Objects.isNull;

public class CreateSavingsEvent {

    private static AtomicLong idGenerator = new AtomicLong();

    public static Optional<SavingsEvent> createSavingsEvent(SavingsRule savingsRule, Transaction transaction) {
        if (!transaction.getDescription().equals("Salary") && (!isNull(savingsRule) && !isNull(transaction))) {
            SavingsEvent savingsEvent = Mapper.of(transaction, SavingsEvent::builder)
                    .map(Transaction::getAmount, transactionAmount -> manageAmountToSave(savingsRule, transaction), SavingsEvent.Builder::withAmount)
//                    .map(Transaction::getExecutionTime, SavingsEvent.Builder::withDate)
                    .map(Transaction::getUserId, SavingsEvent.Builder::withUserId)
                    .map(Transaction::getId, SavingsEvent.Builder::withTriggerId)
                    .map(ruleType -> savingsRule.getRuleType(), SavingsEvent.Builder::withRuleType)
                    .map(savingRuleId -> savingsRule.getId(), SavingsEvent.Builder::withSavingsRuleId)
                    .map(created -> Instant.now(), SavingsEvent.Builder::withCreated)
//                    .map(savingsEventId -> incrementAndGetId(idGenerator), SavingsEvent.Builder::withId)  auto increment
                    .map(savingsGoalId -> savingsRule.consumeSavingsGoal(), SavingsEvent.Builder::withSavingsGoaldId)
                    .build(SavingsEvent.Builder::build);
            return Optional.of(savingsEvent);
        }
        return Optional.empty();
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
            return savingsRule.getPlaceDescription().equals(transaction.getDescription()) ? savingsRule.getAmount() : null;
        }
        return null;
    }
}
