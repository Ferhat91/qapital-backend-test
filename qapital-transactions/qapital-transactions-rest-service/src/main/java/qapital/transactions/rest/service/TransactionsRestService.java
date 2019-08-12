package qapital.transactions.rest.service;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qapital.transactions.domain.Transaction;
import qapital.transactions.service.TransactionsService;

@RestController
@RequestMapping("transactions")
public class TransactionsRestService {

    private final TransactionsService transactionsService;

    public TransactionsRestService(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping
    @RequestMapping("/store-transaction")
    void postTransaction(@RequestBody Transaction transaction){
        transactionsService.storeTransaction(transaction);
    }
}
