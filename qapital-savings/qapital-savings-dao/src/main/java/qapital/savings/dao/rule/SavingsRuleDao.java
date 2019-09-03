package qapital.savings.dao.rule;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import qapital.savings.domain.rule.SavingsRule;
import java.util.List;
import java.util.Optional;

@RegisterRowMapper(SavingsRuleMapper.class)
public interface SavingsRuleDao {

    @SqlQuery("SELECT * FROM 'savings_rule' WHERE user_id = :userId")
    List<SavingsRule> getSavingsRules(@Bind("userId") Long userId);

    @SqlQuery("SELECT * FROM 'savings_rule'")
    List<SavingsRule> getSavingsRules();

    @SqlQuery("SELECT * FROM 'savings_rule' WHERE user_id = :userId AND id = :id")
    Optional<SavingsRule> getSavingsRule(@Bind("userId") Long userId, @Bind("id") Long id);

    @SqlUpdate("INSERT INTO 'savings_rule' (id, user_id, savings_goal_id, rule_type, status, amount, description) " +
            "VALUES (:id, :userId, :savingsGoalId, :ruleType, :status, :amount, :description)")
    void persistSavingsRule(@BindBean SavingsRule savingsRule);
}
