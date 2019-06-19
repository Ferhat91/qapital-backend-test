package qapital.savings.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qapital.savings.domain.event.SavingsEvent;
import qapital.savings.domain.rule.SavingsRule;
import qapital.transactions.domain.Transaction;
import qapital.transactions.service.TransactionsService;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import static java.util.Objects.isNull;
import static qapital.savings.service.SavingsRulesTemplate.getActiveRulesForUser;

@Service
public class StandardSavingsRulesServiceImpl implements SavingsRulesService {

    private static final Logger LOGGER = Logger.getLogger(StandardSavingsRulesServiceImpl.class.getName());

    private final TransactionsService transactionsService;

    private static AtomicLong idGenerator = new AtomicLong();

    @Autowired
    public StandardSavingsRulesServiceImpl(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @Override
    public List<SavingsEvent> executeSavingsRulesOnTransactionsMadeByUser(Long userId) {

      List<SavingsEvent> savingsEvents = List.of();

      if (!isNull(userId)) {
        List<Transaction> transactions = transactionsService.getLatestTransactionsForUser(userId);
        List<SavingsRule> savingsRules = getActiveRulesForUser(userId);

        savingsRules.forEach(rule ->{
            transactions.forEach(transaction -> createSavingsEvent(rule, transaction).ifPresent(savingsEvents::add));
        });
      } else {
        LOGGER.info("Could not generate SavingsEvent for userId: null");
      }
      return savingsEvents;
    }

    private static Long incrementAndGetId(AtomicLong atomicLong){ //Preferably have a common-utility module with utility classes instead of duplicating code.
        return new AtomicLong(atomicLong.incrementAndGet()).get();
    }

    private static Optional<SavingsEvent> createSavingsEvent(SavingsRule savingsRule, Transaction transaction) {
    if (!transaction.getPlaceDescription().equals("Salary") && (!isNull(savingsRule) && !isNull(transaction))) {
      SavingsEvent savingsEvent = Mapper.of(transaction, SavingsEvent::builder)
        .map(Transaction::getAmount, transactionAmount -> manageAmountToSave(savingsRule, transaction), SavingsEvent.Builder::withAmount)
        .map(Transaction::getDate, SavingsEvent.Builder::withDate)
        .map(Transaction::getUserId, SavingsEvent.Builder::withUserId)
        .map(Transaction::getId, SavingsEvent.Builder::withTriggerId)
        .map(ruleType -> savingsRule.getRuleType(), SavingsEvent.Builder::withRuleType)
        .map(savingRuleId -> savingsRule.getId(), SavingsEvent.Builder::withSavingsRuleId)
        .map(created -> Instant.now(), SavingsEvent.Builder::withCreated)
        .map(savingsEventId -> incrementAndGetId(idGenerator), SavingsEvent.Builder::withId)
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
      return savingsRule.getPlaceDescription().equals(transaction.getPlaceDescription()) ? savingsRule.getAmount() : null;
    }
    return null;
  }
}