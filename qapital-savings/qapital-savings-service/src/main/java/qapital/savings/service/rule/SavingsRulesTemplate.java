package qapital.savings.service;

import com.google.common.collect.Lists;
import qapital.savings.domain.rule.RuleType;
import qapital.savings.domain.rule.SavingsRule;
import java.util.List;
import java.util.Optional;
import static java.util.Objects.isNull;

public class  SavingsRulesTemplate {

  public static List<SavingsRule> getActiveRulesForUser(Long userId) {
    List<SavingsRule> savingsRules = Lists.newArrayList();
    createSavingsRule(1L, userId, "Starbucks", RuleType.GUILTY_PLEASURE,3.00D).ifPresent(savingsRules::add);
    createSavingsRule(2L, userId, null, RuleType.ROUND_UP,2.00D).ifPresent(savingsRules::add);
    return savingsRules;
  }

  private static Optional<SavingsRule> createSavingsRule(Long id,
                                                         Long userId,
                                                         String placeDescription ,
                                                         RuleType ruleType,
                                                         Double amount) {
    if(!isNull(id) && !isNull(userId) && !isNull(ruleType) && !isNull(amount)){
      SavingsRule savingsRule = SavingsRule.builder()
              .withId(id)
              .withUserId(userId)
              .withPlaceDescription(placeDescription)
              .withRuleType(ruleType)
              .withAmount(amount)
              .build();
      return Optional.of(savingsRule);
    }
  return Optional.empty();
  }
}
