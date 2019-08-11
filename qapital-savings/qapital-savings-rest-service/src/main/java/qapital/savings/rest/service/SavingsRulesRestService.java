package qapital.savings.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qapital.savings.domain.event.SavingsEvent;
import qapital.savings.domain.rule.SavingsRule;
import qapital.savings.service.SavingsRulesService;
import java.util.List;

@RestController
@RequestMapping("/api/savings/qapital.savings.domain.rule")
public class SavingsRulesRestService {

    private final SavingsRulesService savingsRulesService;

    @Autowired
    public SavingsRulesRestService(SavingsRulesService savingsRulesService) {
        this.savingsRulesService = savingsRulesService;
    }

    @GetMapping("/execute-savings-rules/{userId}")
    public List<SavingsEvent> executeSavingsRulesOnTransactionsMadeByUser(@PathVariable Long userId) {
        List<SavingsEvent> savingsEvents = savingsRulesService.executeSavingsRulesOnTransactionsMadeByUser(userId);
        return savingsEvents;
    }
}
