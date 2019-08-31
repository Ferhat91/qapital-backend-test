package qapital.savings.rest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qapital.savings.domain.event.SavingsEvent;
import qapital.savings.service.event.SavingsEventService;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.isNull;

@RestController
@RequestMapping("/savings-event")
public class SavingsEventRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SavingsEventRestService.class);

    private final SavingsEventService savingsEventService;

    public SavingsEventRestService(SavingsEventService savingsEventService) {
        this.savingsEventService = Objects.requireNonNull(savingsEventService, "savingsEvent");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SavingsEvent>> getSavingsEvents(@PathVariable("userId") Long userId) {
        if(!isNull(userId)){
            LOGGER.info("Attempt to fetch savingsEvents for user {} ", userId);
            List<SavingsEvent> savingsEvents = savingsEventService.getSavingsEvents(userId);
            ResponseEntity.ok(savingsEvents);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    ResponseEntity<String> persistSavingsEvent(@RequestBody SavingsEvent savingsEvent){
        if(!isNull(savingsEvent)){
            LOGGER.info("Attempt to persist savingsEvent");
            savingsEventService.persistSavingsEvent(savingsEvent);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else{
            LOGGER.info("Cannot persist null savingsEvent!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
