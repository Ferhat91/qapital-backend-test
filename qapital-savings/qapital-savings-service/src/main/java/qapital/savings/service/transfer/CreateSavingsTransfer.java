package qapital.savings.service.transfer;


import qapital.savings.domain.event.SavingsEvent;
import qapital.savings.domain.transfer.SavingsTransfer;
import qapital.savings.service.utility.Mapper;
import java.util.Optional;
import static java.util.Objects.isNull;

public class CreateSavingsTransfer {

    public static Optional<SavingsTransfer> createSavingsTransfer(SavingsEvent savingsEvent){

        if(!isNull(savingsEvent)){
            SavingsTransfer savingsTransfer =  Mapper.of(savingsEvent, SavingsTransfer::builder)
                    .map(SavingsEvent::getSavingsTransferId, SavingsTransfer.Builder::withId)
                    .map(SavingsEvent::getId, SavingsTransfer.Builder::withSavingsEventId)
                    .map(SavingsEvent::getUserId, SavingsTransfer.Builder::withUserId)
                    .map(SavingsEvent::getDate, SavingsTransfer.Builder::withTransactionExecutionTime)
                    .build(SavingsTransfer.Builder::build);
            assert savingsTransfer != null;
            return Optional.of(savingsTransfer);
        }
        return Optional.empty();
    }
}
