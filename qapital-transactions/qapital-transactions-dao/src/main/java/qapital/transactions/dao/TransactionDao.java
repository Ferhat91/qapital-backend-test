package qapital.transactions.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import qapital.transactions.dao.mapper.TransactionMapper;
import qapital.transactions.domain.Transaction;
import java.util.List;

@RegisterRowMapper(TransactionMapper.class)
public interface TransactionDao {

    @SqlQuery("SELECT * FROM 'transaction' WHERE user_id = :userId")
    List<Transaction> getTransactions(@Bind("userId") Long userId);

    @SqlQuery("SELECT * FROM 'transaction' WHERE user_id = :userId AND id = :id")
    Transaction getTransaction(@Bind("userId") Long userId, @Bind("id") Long transactionId);

    @SqlQuery("SELECT * FROM 'transaction'")
    List<Transaction> getTransactions();

    @SqlUpdate("INSERT INTO 'transaction' (id, user_id, amount, description, execution_time, type) VALUES (:id, :userId, :amount, :description, :executionTime, :type)")
    void persistTransaction(@BindBean Transaction transaction);
}