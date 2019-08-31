package qapital.savings.rest.service.transfer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qapital.savings.domain.transfer.SavingsTransfer;
import qapital.savings.service.transfer.SavingsTransferService;

import java.util.List;
import java.util.Objects;
import static java.util.Objects.isNull;

@RestController
@RequestMapping("/savings-rules")
public class SavingsTransferRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SavingsTransferRestService.class);

    private SavingsTransferService savingsTransferService;

    public SavingsTransferRestService(SavingsTransferService savingsTransferService) {
        this.savingsTransferService = Objects.requireNonNull(savingsTransferService, "savingsTransfer");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SavingsTransfer>> getSavingsRule(@PathVariable("userId") Long userId) {
        if (!isNull(userId)) {
            LOGGER.info("Attempt to fetch savingsRule(s) for user {} ", userId);
            List<SavingsTransfer> savingsTransfers = savingsTransferService.getSavingsTransfers(userId);
            ResponseEntity.ok(savingsTransfers);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    ResponseEntity<String> persistSavingsTransfer(@RequestBody SavingsTransfer savingsTransfer) {
        if (!isNull(savingsTransfer)) {
            LOGGER.info("Attempt to persist savingsRule");
            savingsRulesService.persistSavingsRule(savingsRule);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            LOGGER.info("Cannot persist null savingsRule!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}