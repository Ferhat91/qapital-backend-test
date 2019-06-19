package qapital.savings.service;

  import qapital.savings.domain.event.SavingsEvent;
  import java.util.List;

public interface SavingsRulesService {

    /**
     * Executes the logic for given savings rule
     * @param userId the configured savings rule
     * @return a list of savings events that are the result of the execution of the rule
     */
    List<SavingsEvent> executeSavingsRulesOnTransactionsMadeByUser(Long userId);

}
