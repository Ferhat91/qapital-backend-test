package qapital.transactions.dao.mapper;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import qapital.transactions.domain.Transaction;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionMapper  implements RowMapper<Transaction> {

    @Override
    public Transaction map(ResultSet rs, StatementContext ctx) throws SQLException {
        return Transaction.builder()
                .withId(rs.getLong(TransactionDatabaseConstants.ID))
                .withUserId(rs.getLong(TransactionDatabaseConstants.USER_ID))
                .withAmount(rs.getDouble(TransactionDatabaseConstants.AMOUNT))
                .withPurchaseDescription(rs.getString(TransactionDatabaseConstants.PURCHASE_DESCRIPTION))
                .withExecutionTime(rs.getTimestamp(TransactionDatabaseConstants.EXECUTION_TIME))
                .build();
    }
}