package qapital.savings.dao.event;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import qapital.savings.domain.event.SavingsEvent;
import java.util.List;

@RegisterRowMapper(SavingsEventMapper.class)
public interface SavingsEventDao {

    @SqlQuery("SELECT * FROM 'savings_event' WHERE user_id = :userId")
    List<SavingsEvent> getSavingsEvents(@Bind("userId") Long userId);

    @SqlQuery("SELECT * FROM 'savings_event' WHERE id = :id AND user_id = :userId")
    SavingsEvent getSavingsEvent(@Bind("id") Long id, @Bind("userId") Long userId);

    @SqlQuery("SELECT * FROM 'savings_event' WHERE  trigger_id = :transactionId AND user_id = :userId")
    SavingsEvent getSavingsEventForTransaction(@Bind("transactionId") Long transactionId, @Bind("userId") Long userId);

    @SqlUpdate("INSERT INTO 'savings_event' (id, user_id, trigger_id, savings_goal_id, savings_rule_id, savings_transfer_id , amount, created, date, cancellation, event_type, rule_type) " +
            "VALUES (:id, :userId, :triggerId, :savingsGoalId, :savingsRuleId, :savingsTransferId ,:amount, :created, :date, :cancellation, :eventType, :ruleType)")
    void persistSavingsEvent(@BindBean SavingsEvent savingsEvent);
}