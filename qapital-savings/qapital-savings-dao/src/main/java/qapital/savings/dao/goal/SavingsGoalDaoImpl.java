package qapital.savings.dao.goal;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlite3.SQLitePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qapital.savings.domain.goal.SavingsGoal;
import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

public class SavingsGoalDaoImpl implements SavingsGoalDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SavingsGoalDaoImpl.class);

    private Jdbi jdbi;

    public SavingsGoalDaoImpl(DataSource dataSource) {
        this.jdbi = Objects.requireNonNull(Jdbi.create(dataSource)
                .installPlugin(new SQLitePlugin())
                .installPlugin(new SqlObjectPlugin()), "dataSource");
    }

    @Override
    public List<SavingsGoal> getSavingsGoals(Long userId) {
        List<SavingsGoal> savingsGoals = jdbi.withExtension(SavingsGoalDao.class, dao -> dao.getSavingsGoals(userId));
        LOGGER.info("Successfully fetched {} savingGoal(s) for userId: {}", savingsGoals.size(), userId);
        return savingsGoals;
    }

    @Override
    public SavingsGoal getSavingsGoal(Long id, Long userId) {
        SavingsGoal savingsGoal = jdbi.withExtension(SavingsGoalDao.class, dao -> dao.getSavingsGoal(id ,userId));
        LOGGER.info("Successfully fetched {} savingGoal {} for userId: {}", id, userId);
        return savingsGoal;
    }

    @Override
    public void persistSavingsGoal(SavingsGoal savingsGoal) {
        LOGGER.info("Storing savingsGoal {} for userId {}", savingsGoal.getId(), savingsGoal.getUserId());
        jdbi.open().attach(SavingsGoalDao.class).persistSavingsGoal(savingsGoal);
    }
}
