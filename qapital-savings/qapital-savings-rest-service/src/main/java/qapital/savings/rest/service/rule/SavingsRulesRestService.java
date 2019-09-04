package qapital.savings.rest.service.rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qapital.savings.domain.rule.SavingsRule;
import qapital.savings.service.rule.SavingsRulesService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static java.util.Objects.isNull;

@RestController
@RequestMapping("/savings-rule")
public class SavingsRulesRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SavingsRulesRestService.class);

    private final SavingsRulesService savingsRulesService;

    public SavingsRulesRestService(SavingsRulesService savingsRulesService) {
        this.savingsRulesService = Objects.requireNonNull(savingsRulesService,"savingsRuleService");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SavingsRule>> getSavingsRules(@PathVariable("userId") Long userId) {
        if(!isNull(userId)){
            LOGGER.info("Attempt to fetch savingsRule(s) for user {} ", userId);
            List<SavingsRule> savingsRules = savingsRulesService.getSavingsRules(userId);
            return ResponseEntity.ok(savingsRules);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{userId}/{id}")
    public ResponseEntity<SavingsRule> getSavingsRule(@PathVariable("userId") Long userId, @PathVariable("id") Long id) {
        if(!isNull(userId) && !isNull(id)){
            LOGGER.info("Attempt to fetch savingsRule {} for user {} ", id ,userId);
            Optional<SavingsRule> savingsRule = savingsRulesService.getSavingsRule(userId,id);
            return savingsRule.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    ResponseEntity<String> persistSavingsRule(@RequestBody SavingsRule savingsRule){
        if(!isNull(savingsRule.getUserId())){
            LOGGER.info("Attempt to persist savingsRule");
            savingsRulesService.persistSavingsRule(savingsRule);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else{
            LOGGER.info("Cannot persist null savingsRule!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
