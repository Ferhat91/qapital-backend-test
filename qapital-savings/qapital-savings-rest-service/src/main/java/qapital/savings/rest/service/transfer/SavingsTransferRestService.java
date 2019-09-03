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
@RequestMapping("/savings-transfer")
public class SavingsTransferRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SavingsTransferRestService.class);

    private SavingsTransferService savingsTransferService;

    public SavingsTransferRestService(SavingsTransferService savingsTransferService) {
        this.savingsTransferService = Objects.requireNonNull(savingsTransferService, "savingsTransfer");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SavingsTransfer>> getSavingsTransfers(@PathVariable("userId") Long userId) {
        if (!isNull(userId)) {
            LOGGER.info("Attempt to fetch savingsRule(s) for user {} ", userId);
            List<SavingsTransfer> savingsTransfers = savingsTransferService.getSavingsTransfers(userId);
            return ResponseEntity.ok(savingsTransfers);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}/{userId}")
    public ResponseEntity<SavingsTransfer> getSavingsTransfer(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        if (!isNull(userId) && !isNull(id)) {
            LOGGER.info("Attempt to fetch savingsRule {} for user {} ", id, userId);
            SavingsTransfer savingsTransfer = savingsTransferService.getSavingsTransfer(id, userId);
            return ResponseEntity.ok(savingsTransfer);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    ResponseEntity<String> persistSavingsTransfer(@RequestBody SavingsTransfer savingsTransfer) {
        if (!isNull(savingsTransfer.getId())) {
            LOGGER.info("Attempt to persist savingsTransfer");
            savingsTransferService.persistSavings(savingsTransfer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            LOGGER.info("Cannot persist null savingsRule!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}