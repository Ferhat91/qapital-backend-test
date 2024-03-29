package qapital.transactions.rest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qapital.transactions.domain.Transaction;
import qapital.transactions.service.TransactionsService;
import java.util.List;
import static java.util.Objects.isNull;

@RestController
@RequestMapping("/transactions")
public class TransactionsRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionsRestService.class);

    private final TransactionsService transactionsService;

    public TransactionsRestService(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping
    ResponseEntity<String> persistTransaction(@RequestBody Transaction transaction){
        if(!isNull(transaction)){
            LOGGER.info("Attempt to persist transaction");
            transactionsService.persistTransaction(transaction);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else{
            LOGGER.info("Cannot persist null transaction!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    ResponseEntity<List<Transaction>> getTransactions(){
        LOGGER.info("Attempt to fetch transaction(s)");
        List<Transaction> transactions = transactionsService.getTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping
    @RequestMapping("/{userId}")
    ResponseEntity<List<Transaction>> getTransactions(@PathVariable("userId") Long id){
       if(!isNull(id)){
           LOGGER.info("Attempt to fetch transaction(s) for userId: {}", id);
           List<Transaction> transactions = transactionsService.getTransactions(id);
           return ResponseEntity.ok(transactions);
       }else{
           LOGGER.info("Cannot fetch transaction(s) for userId: null");
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping
    @RequestMapping("/{userId}/{transactionId}")
    ResponseEntity<Transaction> getTransaction(@PathVariable("userId") Long userId,
                                               @PathVariable("transactionId") Long transactionId){ //should be id not trainsactionId
        if(!isNull(userId) && !isNull(transactionId)){
            LOGGER.info("Attempt to fetch transaction: {} for userId: {}", transactionId,userId);
            Transaction transactions = transactionsService.getTransaction(userId, transactionId);
            return ResponseEntity.ok(transactions);
        }else{
            LOGGER.info("Cannot fetch transaction: {} for userId: {}", transactionId, userId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
