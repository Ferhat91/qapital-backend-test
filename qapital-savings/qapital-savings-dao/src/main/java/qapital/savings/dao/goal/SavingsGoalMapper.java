package qapital.savings.dao.goal;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import qapital.savings.dao.SavingsDatabaseUtility;
import qapital.savings.domain.goal.SavingsGoal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SavingsGoalMapper implements RowMapper<SavingsGoal> {

    @Override
    public SavingsGoal map(ResultSet rs, StatementContext ctx) throws SQLException {
        return SavingsGoal.builder()
                .withId(rs.getLong(SavingsDatabaseUtility.ID))
                .withUserId(rs.getLong(SavingsDatabaseUtility.USER_ID))
                .withSavingsRuleId(rs.getLong(SavingsDatabaseUtility.SAVINGS_RULE_ID))
                .withSavingsTransferId(rs.getLong(SavingsDatabaseUtility.SAVINGS_TRANSFER_ID))
                .withDescription(rs.getString(SavingsDatabaseUtility.DESCRIPTION))
                .withRequiredAmount(rs.getDouble(SavingsDatabaseUtility.REQUIRED_AMOUNT))
                .build();
    }
}