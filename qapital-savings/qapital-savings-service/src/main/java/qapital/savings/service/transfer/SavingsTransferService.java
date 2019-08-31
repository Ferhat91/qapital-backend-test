package qapital.savings.service.transfer;

import qapital.savings.domain.transfer.SavingsTransfer;

import java.util.List;

public interface SavingsTransferService {

    void persistSavings(SavingsTransfer savingsTransfer);

    List<SavingsTransfer> getSavingsTransfers(Long userId);
}
