package qapital.savings.dao.mapper;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import qapital.savings.domain.rule.RuleType;
import qapital.savings.domain.rule.SavingsRule;
import qapital.savings.domain.rule.Status;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SavingsRuleMapper  implements RowMapper<SavingsRule> {

    @Override
    public SavingsRule map(ResultSet rs, StatementContext ctx) throws SQLException {
        return SavingsRule.builder()
                .withId(rs.getLong(SavingsDatabaseUtility.ID))
                .withUserId(rs.getLong(SavingsDatabaseUtility.USER_ID))
                .withDescription(rs.getString(SavingsDatabaseUtility.DESCRIPTION))
                .withAmount(rs.getDouble(SavingsDatabaseUtility.AMOUNT))
                .withRuleType(RuleType.valueOf(rs.getString(SavingsDatabaseUtility.RULE_TYPE)))
                .withStatus(Status.valueOf(rs.getString(SavingsDatabaseUtility.STATUS)))
//                .withSavingGoalIds()
                .build();
    }

}