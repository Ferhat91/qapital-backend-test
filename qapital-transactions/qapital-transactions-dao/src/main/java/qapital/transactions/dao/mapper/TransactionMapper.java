package qapital.transactions.dao.mapper;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import qapital.transactions.domain.Transaction;
import qapital.transactions.domain.TransactionType;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.util.Objects.isNull;

public class TransactionMapper implements RowMapper<Transaction> {

    @Override
    public Transaction map(ResultSet rs, StatementContext ctx) throws SQLException {
        return Transaction.builder()
                .withId(rs.getLong(TransactionDatabaseUtility.ID))
                .withUserId(rs.getLong(TransactionDatabaseUtility.USER_ID))
                .withAmount(rs.getDouble(TransactionDatabaseUtility.AMOUNT))
                .withdDescription(rs.getString(TransactionDatabaseUtility.DESCRIPTION))
                .withExecutionTime(rs.getString(TransactionDatabaseUtility.EXECUTION_TIME))
                .withTransactionType(mapTransactionType(rs.getString(TransactionDatabaseUtility.TRANSACTION_TYPE)))
                .build();
    }

    private TransactionType mapTransactionType(String transactionType){
        return !isNull(transactionType) ? TransactionType.valueOf(transactionType) : null;
    }
}