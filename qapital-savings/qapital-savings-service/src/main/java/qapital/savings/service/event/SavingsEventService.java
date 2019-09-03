package qapital.savings.service.event;

import qapital.savings.domain.event.SavingsEvent;
import java.util.List;

public interface SavingsEventService {

    List<SavingsEvent> getSavingsEvents(Long userId);

    SavingsEvent getSavingsEvent(Long id, Long userId);

    SavingsEvent getSavingsEventForTransaction(Long transactionId, Long userId);

    void persistSavingsEvent(SavingsEvent savingsEvent);
}
