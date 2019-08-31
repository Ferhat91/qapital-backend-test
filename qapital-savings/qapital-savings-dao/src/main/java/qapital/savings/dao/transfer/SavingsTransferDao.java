package qapital.savings.dao.transfer;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import qapital.savings.domain.transfer.SavingsTransfer;
import java.util.List;

@RegisterRowMapper(SavingsTransferMapper.class)
public interface SavingsTransferDao {

    @SqlUpdate("INSERT into 'savings_transfer' (id, user_id , savings_event_id, transaction_execution_time) " +
            "VALUES (:id, :user_id , :savings_event_id, :transaction_execution_time)")
    void persistSavings(@BindBean SavingsTransfer savingsTransfer);

    @SqlQuery("SELECT * FROM 'savings_transfer' WHERE user_id = userId")
    List<SavingsTransfer> getSavingsTransfers(@Bind("userId") Long userId);
}
