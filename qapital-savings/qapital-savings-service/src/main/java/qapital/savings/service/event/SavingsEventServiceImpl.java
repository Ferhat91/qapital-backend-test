package qapital.savings.service.event;

import qapital.savings.dao.event.SavingsEventDao;
import qapital.savings.domain.event.SavingsEvent;
import java.util.List;
import java.util.Objects;

public class SavingsEventServiceImpl implements SavingsEventService {

    private SavingsEventDao savingsEventDao;

    public SavingsEventServiceImpl(SavingsEventDao savingsEventDao) {
        this.savingsEventDao = Objects.requireNonNull(savingsEventDao, "savingsEventDao");
    }

    @Override
    public List<SavingsEvent> getSavingsEvents(Long userId) {
        List<SavingsEvent> savingsEvents = savingsEventDao.getSavingsEvents(userId);
        return savingsEvents;
    }

    @Override
    public SavingsEvent getSavingsEvent(Long id, Long userId) {
        SavingsEvent savingsEvent = savingsEventDao.getSavingsEvent(id, userId);
        return savingsEvent;
    }

    @Override
    public void persistSavingsEvent(SavingsEvent savingsEvent) {
        savingsEventDao.persistSavingsEvent(savingsEvent);
    }
}
