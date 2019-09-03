import org.json.JSONObject
import qapital.savings.domain.event.EventType
import qapital.savings.domain.rule.RuleType
import qapital.savings.domain.rule.Status
import java.time.LocalDate
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric

trait TestDataContext {

    static createSavingsGoal() {
        return [
                id            : uniqueRandomNumeric(getRandomIntegerInRange(2, 4)),
                userId        : 1,
                requiredAmount: generatePositiveRandomDouble(),
                ruleType      : RuleType.GUILTY_PLEASURE.getRuleTypeValue(),
                savingsRuleId: getRandomIntegerInRange(1, 10),
                savingsTransferId: getRandomIntegerInRange(1,10),
                description   : "SAVING FOR LEAVING EARTH!",
        ]
    }

    static createSavingsEvent() {
        return [
                id            : Long.valueOf(uniqueRandomNumeric(getRandomIntegerInRange(2, 4))),
                userId        : getRandomIntegerInRange(1, 10),
                amount        : generatePositiveRandomDouble(),
                ruleType      : RuleType.GUILTY_PLEASURE.getRuleTypeValue(),
                savingsGoalId : getRandomIntegerInRange(1, 10),
                savingsRuleId: getRandomIntegerInRange(1, 10),
                savingsTransferId: getRandomIntegerInRange(1,10),
                cancellation:  false,
                created:    LocalDate.now().toString(),
                date:    LocalDate.now().toString(),
                eventType:  EventType.RULE_APPLICATION.getEventTypeValue(),
                triggerId: getRandomIntegerInRange(1,10),
        ]
    }

    static createSavingsRule() {
        def description = guiltyPleasureDescriptions.get(getRandomIntegerInRange(0, guiltyPleasureDescriptions.size() - 1))
        return [
                id            : Long.valueOf(uniqueRandomNumeric(getRandomIntegerInRange(2, 4))),
                userId        : 1,
                amount        : generatePositiveRandomDouble(),
                description   : description,
                ruleType      : RuleType.GUILTY_PLEASURE.getRuleTypeValue(),
                savingsGoalId : getRandomIntegerInRange(1, 10),
                status        : Status.ACTIVE.getStatusValue()
        ]
    }

    static guiltyPleasureDescriptions = ["Starbucks", "McDonald's", "Apple Itunes", "Amazon", "Walmart", "Papa Joe's"]

    static JSONObject makeSavingsGoalJSON(Object savingsGoal) {
        JSONObject actual = new JSONObject()
        actual.put("id", savingsGoal.id as Long)
        actual.put("userId",  savingsGoal.userId)
        actual.put("savingsRuleId", savingsGoal.savingsRuleId)
        actual.put("savingsTransferId", savingsGoal.savingsTransferId)
        actual.put("description", savingsGoal.description)
        actual.put("requiredAmount",  savingsGoal.requiredAmount)
        return actual
    }

    static JSONObject makeSavingsEventSON(Object savingsEvent) {
        JSONObject actual = new JSONObject()
        actual.put("id", savingsEvent.id)
        actual.put("userId",  savingsEvent.userId as Long)
        actual.put("savingsGoalId",  savingsEvent.savingsGoalId as Long)
        actual.put("savingsTransferId", savingsEvent.savingsTransferId as Long)
        actual.put("savingsRuleId", savingsEvent.savingsRuleId as Long)
        actual.put("amount", savingsEvent.amount)
        actual.put("ruleType",  savingsEvent.ruleType)
        actual.put("cancellation", savingsEvent.cancellation)
        actual.put("created", savingsEvent.created)
        actual.put("date", savingsEvent.date)
        actual.put("eventType", savingsEvent.eventType)
        actual.put("triggerId", savingsEvent.triggerId)
        return actual
    }

    static JSONObject makeSavingsRuleJSON(Object savingsRule) {
        JSONObject actual = new JSONObject()
        actual.put("id", savingsRule.id)
        actual.put("userId",  savingsRule.userId)
        actual.put("savingsGoalId",  savingsRule.savingsGoalId)
        actual.put("amount",  savingsRule.amount)
        actual.put("description",  savingsRule.description)
        actual.put("ruleType",  savingsRule.ruleType)
        actual.put("status", savingsRule.status)
        return actual
    }

    static randomStore = [:]

    static Integer uniqueRandomNumeric(Integer length) {
        if (!randomStore[length]) {
            randomStore[length] = []
        }
        length = length <= 0 ? -length : length
        String random = randomNumeric(length)

        while (randomStore[length].contains(random)) {
            random = randomNumeric(length)
        }
        return Math.abs(random as Integer)
    }

    static Integer getRandomIntegerInRange(Integer min, Integer max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return new Random().nextInt((max - min) + 1) + min;
    }

    static Double generatePositiveRandomDouble() {
        return new Random().nextDouble().round()
    }
}