package qapital.savings.rest.service.goal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qapital.savings.domain.goal.SavingsGoal;
import qapital.savings.service.goal.SavingsGoalService;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/savings-goal")
public class SavingsGoalRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SavingsGoalRestService.class);

    private final SavingsGoalService savingsGoalService;

    public SavingsGoalRestService(SavingsGoalService savingsGoalService) {
        this.savingsGoalService = Objects.requireNonNull(savingsGoalService, "savingsGoalService");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SavingsGoal>> getSavingsGoals(@PathVariable("userId") Long userId) {
        if(!isNull(userId)){
            LOGGER.info("Attempt to fetch savingsEvent(s) for user {} ", userId);
            List<SavingsGoal> savingsGoals = savingsGoalService.getSavingsGoals(userId);
            ResponseEntity.ok(savingsGoals);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/{id}/{userId}")
    public ResponseEntity<SavingsGoal> getSavingsGoal(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        if(!isNull(userId) && !isNull(id)){
            LOGGER.info("Attempt to fetch savingsEvent {} for user {} ", id, userId);
            SavingsGoal savingsGoals = savingsGoalService.getSavingsGoal(id, userId);
            return ResponseEntity.ok(savingsGoals);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    ResponseEntity<String> persistSavingsGoal(@RequestBody SavingsGoal savingsGoal){
        if(!isNull(savingsGoal)){
            LOGGER.info("Attempt to persist savingsGoal");
            savingsGoalService.persistSavingsGoal(savingsGoal);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else{
            LOGGER.info("Cannot persist null savingsEvent!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
