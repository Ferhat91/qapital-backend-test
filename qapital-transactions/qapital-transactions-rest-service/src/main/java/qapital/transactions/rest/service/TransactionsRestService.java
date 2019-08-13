package qapital.transactions.rest.service;


import org.springframework.web.bind.annotation.*;
import qapital.transactions.domain.Transaction;
import qapital.transactions.service.TransactionsService;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionsRestService {

    private final TransactionsService transactionsService;

    public TransactionsRestService(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @GetMapping
    @RequestMapping("/{userId}/transactions")
    List<Transaction> getTransactions(@PathVariable("userId") Long id){
        List<Transaction> transactions = transactionsService.getTransactions(id);
        return transactions;
    }

    @GetMapping
    @RequestMapping("/transactions")
    List<Transaction> getTransactions(){
        List<Transaction> transactions = transactionsService.getTransactions();
        return transactions;
    }

    @PostMapping
    @RequestMapping("/store-transaction")
    void postTransaction(@RequestBody Transaction transaction){
        transactionsService.storeTransaction(transaction);
    }
}
