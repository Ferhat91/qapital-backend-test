package qapital.savings.service.goal;

import qapital.savings.domain.goal.SavingsGoal;
import java.util.List;

public interface SavingsGoalService {

    List<SavingsGoal> getSavingsGoals(Long userId);

    SavingsGoal getSavingsGoal(Long id, Long userId);

    void persistSavingsGoal(SavingsGoal savingsGoal);
}
