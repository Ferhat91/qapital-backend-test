package qapital.savings.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qapital.savings.domain.event.SavingsEvent;
import qapital.savings.domain.rule.SavingsRule;
import qapital.transactions.domain.Transaction;
import qapital.transactions.service.TransactionsService;
import java.util.List;
import java.util.logging.Logger;
import static java.util.Objects.isNull;
import static qapital.savings.service.CreateSavingsEvent.createSavingsEvent;
import static qapital.savings.service.SavingsRulesTemplate.getActiveRulesForUser;

@Service
public class StandardSavingsRulesServiceImpl implements SavingsRulesService {

    private static final Logger LOGGER = Logger.getLogger(StandardSavingsRulesServiceImpl.class.getName());

    private final TransactionsService transactionsService;

    @Autowired
    public StandardSavingsRulesServiceImpl(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @Override
    public List<SavingsEvent> executeSavingsRulesOnTransactionsMadeByUser(Long userId) {

      List<SavingsEvent> savingsEvents = Lists.newArrayList();

      if (!isNull(userId)) {

        List<Transaction> transactions = transactionsService.getTransactions(userId);
        List<SavingsRule> savingsRules = getActiveRulesForUser(userId);

        savingsRules.forEach(rule ->{
            transactions.forEach(transaction -> createSavingsEvent(rule, transaction).ifPresent(savingsEvents::add));
        });

      } else {
        LOGGER.info("Could not generate SavingsEvent for userId: null");
      }
      return savingsEvents;
    }
}