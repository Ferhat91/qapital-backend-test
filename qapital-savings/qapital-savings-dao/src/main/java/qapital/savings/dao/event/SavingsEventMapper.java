package qapital.savings.dao.event;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import qapital.savings.dao.SavingsDatabaseUtility;
import qapital.savings.domain.event.EventType;
import qapital.savings.domain.event.SavingsEvent;
import qapital.savings.domain.rule.RuleType;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SavingsEventMapper implements RowMapper<SavingsEvent> {

    @Override
    public SavingsEvent map(ResultSet rs, StatementContext ctx) throws SQLException {
        return SavingsEvent.builder()
                .withId(rs.getLong(SavingsDatabaseUtility.ID))
                .withUserId(rs.getLong(SavingsDatabaseUtility.USER_ID))
                .withTriggerId(rs.getLong(SavingsDatabaseUtility.TRIGGER_ID))
                .withSavingsGoaldId(rs.getLong(SavingsDatabaseUtility.SAVINGS_GOAL_ID))
                .withSavingsRuleId(rs.getLong(SavingsDatabaseUtility.SAVINGS_RULE_ID))
                .withSavingTransferId(rs.getLong(SavingsDatabaseUtility.SAVINGS_TRANSFER_ID))
                .withAmount(rs.getDouble(SavingsDatabaseUtility.AMOUNT))
                .withCreated(rs.getString(SavingsDatabaseUtility.CREATED))
                .withDate(rs.getString(SavingsDatabaseUtility.DATE))
                .withCancellation(rs.getBoolean(SavingsDatabaseUtility.CANCELLATION))
                .withEventType(EventType.valueOf(rs.getString(SavingsDatabaseUtility.EVENT_TYPE)))
                .withRuleType(RuleType.valueOf(rs.getString(SavingsDatabaseUtility.RULE_TYPE)))
                .build();
    }
}
