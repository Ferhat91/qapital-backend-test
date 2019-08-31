package qapital.savings.dao.transfer;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import qapital.savings.dao.SavingsDatabaseUtility;
import qapital.savings.domain.transfer.SavingsTransfer;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SavingsTransferMapper implements RowMapper<SavingsTransfer> {
    @Override
    public SavingsTransfer map(ResultSet rs, StatementContext ctx) throws SQLException {
        SavingsTransfer savingsTransfer =  SavingsTransfer.builder()
                .withId(rs.getLong(SavingsDatabaseUtility.ID))
                .withSavingsEventId(rs.getLong(SavingsDatabaseUtility.SAVINGS_EVENT_ID))
                .withTransactionExecutionTime(rs.getString(SavingsDatabaseUtility.TRANSACTION_EXECUTION_TIME))
                .withUserId(rs.getLong(SavingsDatabaseUtility.USER_ID))
                .build();
        return savingsTransfer;
    }
}
