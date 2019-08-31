package qapital.savings.rest.service.rules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qapital.savings.domain.rule.SavingsRule;
import qapital.savings.service.rule.SavingsRulesService;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.isNull;

@RestController
@RequestMapping("/savings-rules")
public class SavingsRulesRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SavingsRulesRestService.class);

    private final SavingsRulesService savingsRulesService;

    public SavingsRulesRestService(SavingsRulesService savingsRulesService) {
        this.savingsRulesService = Objects.requireNonNull(savingsRulesService,"savingsRuleService");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SavingsRule>> getSavingsRule(@PathVariable("userId") Long userId) {
        if(!isNull(userId)){
            LOGGER.info("Attempt to fetch savingsRule(s) for user {} ", userId);
            List<SavingsRule> savingsRules = savingsRulesService.getSavingsRules(userId);
            ResponseEntity.ok(savingsRules);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    ResponseEntity<String> persistSavingsRule(@RequestBody SavingsRule savingsRule){
        if(!isNull(savingsRule)){
            LOGGER.info("Attempt to persist savingsRule");
            savingsRulesService.persistSavingsRule(savingsRule);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else{
            LOGGER.info("Cannot persist null savingsRule!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
