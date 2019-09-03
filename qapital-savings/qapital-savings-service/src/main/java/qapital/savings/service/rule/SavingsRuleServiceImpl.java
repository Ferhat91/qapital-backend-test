package qapital.savings.service.rule;

import qapital.savings.dao.rule.SavingsRuleDao;
import qapital.savings.domain.rule.SavingsRule;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SavingsRuleServiceImpl implements SavingsRulesService {

    private SavingsRuleDao savingsRuleDao;

    public SavingsRuleServiceImpl(SavingsRuleDao savingsRuleDao) {
        this.savingsRuleDao = Objects.requireNonNull(savingsRuleDao, "savingsRuleDao");
    }

    @Override
    public List<SavingsRule> getSavingsRules(Long userId) {
        List<SavingsRule> savingsRules = savingsRuleDao.getSavingsRules(userId);
        return savingsRules;
    }

    @Override
    public Optional<SavingsRule> getSavingsRule(Long userId, Long id) {
        Optional<SavingsRule> savingsRule = savingsRuleDao.getSavingsRule(userId, id);
        return savingsRule;
    }


    @Override
    public void persistSavingsRule(SavingsRule savingsRule) {
        savingsRuleDao.persistSavingsRule(savingsRule);
    }
}
