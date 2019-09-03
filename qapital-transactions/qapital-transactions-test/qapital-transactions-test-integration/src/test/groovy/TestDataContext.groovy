import org.json.JSONObject
import qapital.transactions.domain.TransactionType
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric

trait TestDataContext {

    static makeTransaction() {
        def description = transactionDescriptions.get(getRandomIntegerInRange(0, transactionDescriptions.size() - 1))
        return [
                id           : uniqueRandomNumeric(getRandomIntegerInRange(2, 4)),
                userId       : 1,
                executionTime: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Timestamp.from(Instant.now())),
                amount       : description == "Salary" ? generatePositiveRandomDouble() : generateNegativeRandomDouble(),
                description  : description,
                type         : TransactionType.RULE_APPLICATION.getTransactionTypeValue(),
        ]
    }

    static JSONObject makeJSONActual(Object transaction) {
        JSONObject actual = new JSONObject()
        actual.put("id", transaction.id as Long)
        actual.put("userId", transaction.userId as Long)
        actual.put("executionTime", transaction.executionTime)
        actual.put("amount", transaction.amount)
        actual.put("description", transaction.description)
        actual.put("type", transaction.type)
        return actual
    }

    static randomStore = [:]

    static String uniqueRandomNumeric(Integer length) {
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

    static transactionDescriptions = ["Starbucks", "McDonald's", "Apple Itunes", "Amazon", "Walmart", "Papa Joe's"] //"Salary" for texsting since we are not applying any svingsRules on salary based transacations!

    static Integer getRandomIntegerInRange(Integer min, Integer max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return new Random().nextInt((max - min) + 1) + min;
    }

    static Double generateNegativeRandomDouble() {
        return -new Random().nextDouble();
    }

    static Double generatePositiveRandomDouble() {
        return new Random().nextDouble();
    }
}