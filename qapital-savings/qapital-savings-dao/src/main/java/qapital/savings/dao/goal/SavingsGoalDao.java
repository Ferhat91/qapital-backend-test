package qapital.savings.dao.goal;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import qapital.savings.domain.goal.SavingsGoal;
import java.util.List;

@RegisterRowMapper(SavingsGoalMapper.class)
public interface SavingsGoalDao {

    @SqlQuery("SELECT * FROM 'savings_goal' WHERE user_id = :userId")
    List<SavingsGoal> getSavingsGoals(@Bind("userId") Long userId);

    @SqlQuery("SELECT * FROM 'savings_goal' WHERE id = :id AND user_id = :userId")
    SavingsGoal getSavingsGoal(@Bind("id") Long id, @Bind("userId") Long userId);

    @SqlUpdate("INSERT INTO 'savings_goal' (id, user_id, savings_rule_id, savings_transfer_id, required_amount, description)" +
            " VALUES (:id, :userId, :savingsRuleId, :savingsTransferId, :requiredAmount, :description)")
    void persistSavingsGoal(@BindBean SavingsGoal savingsGoal);
}


