package qapital.savings.service.transfer;

import qapital.savings.dao.transfer.SavingsTransferDao;
import qapital.savings.domain.transfer.SavingsTransfer;
import java.util.List;
import java.util.Objects;

public class SavingsTransferServiceImpl implements SavingsTransferService {

    private SavingsTransferDao savingsTransferDao;

    public SavingsTransferServiceImpl(SavingsTransferDao savingsTransferDao) {
        this.savingsTransferDao = Objects.requireNonNull(savingsTransferDao);
    }

    @Override
    public void persistSavings(SavingsTransfer savingsTransfer) {
        savingsTransferDao.persistSavings(savingsTransfer);
    }

    @Override
    public List<SavingsTransfer> getSavingsTransfers(Long userId) {
        List<SavingsTransfer> savingsTransfers = savingsTransferDao.getSavingsTransfers(userId);
        return savingsTransfers;
    }
}
