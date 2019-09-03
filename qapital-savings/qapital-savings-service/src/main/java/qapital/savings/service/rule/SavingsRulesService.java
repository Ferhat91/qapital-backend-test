package qapital.savings.service.rule;

import qapital.savings.domain.rule.SavingsRule;
import java.util.List;
import java.util.Optional;

public interface SavingsRulesService {

    List<SavingsRule> getSavingsRules(Long userId);

    Optional<SavingsRule> getSavingsRule(Long userId, Long id);

    void persistSavingsRule(SavingsRule savingsRule);
}
