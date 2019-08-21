package qapital.savings.dao;

import qapital.savings.domain.event.SavingsEvent;

import java.util.List;

public interface SavingsRuleDao {

    List<SavingsEvent> getSavingsEvents();

}
