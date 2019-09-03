package qapital.savings.service.goal;

import qapital.savings.dao.goal.SavingsGoalDao;
import qapital.savings.domain.goal.SavingsGoal;
import java.util.List;
import java.util.Objects;

public class SavingsGoalServiceImpl implements SavingsGoalService {

    private SavingsGoalDao savingsGoalDao;

    public SavingsGoalServiceImpl(SavingsGoalDao savingsGoalDao) {
        this.savingsGoalDao = Objects.requireNonNull(savingsGoalDao, "savingsGoalDao");
    }

    @Override
    public List<SavingsGoal> getSavingsGoals(Long userId) {
        List<SavingsGoal> savingsGoals = savingsGoalDao.getSavingsGoals(userId);
        return savingsGoals;
    }

    @Override
    public SavingsGoal getSavingsGoal(Long id, Long userId) {
        SavingsGoal savingsGoal = savingsGoalDao.getSavingsGoal(id, userId);
        return savingsGoal;
    }

    @Override
    public void persistSavingsGoal(SavingsGoal savingsGoal) {
        savingsGoalDao.persistSavingsGoal(savingsGoal);
    }
}
