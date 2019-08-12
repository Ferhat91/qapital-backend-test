package qapital.transactions.dao;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import qapital.transactions.dao.mapper.BindTransaction;
import qapital.transactions.dao.mapper.TransactionMapper;
import qapital.transactions.domain.Transaction;

import java.util.List;

public interface TransactionDao {

    @SqlQuery("SELECT * FROM transaction WHERE user_id = :userId")
    @RegisterBeanMapper(TransactionMapper.class)
    List<Transaction> getTransactions(Long userId);

    @SqlQuery("SELECT * FROM transaction WHERE user_id = :userId")
    List<Transaction> getTransactions();

    @SqlUpdate("INSERT INTO transaction t (id, user_id, amount, purchase_description, execution_time) VALUES (t.id, t.user_id, t.amount, t.purchase_description, t.execution_time)")
    void storeTransaction(@BindTransaction("t") Transaction transaction);
}
