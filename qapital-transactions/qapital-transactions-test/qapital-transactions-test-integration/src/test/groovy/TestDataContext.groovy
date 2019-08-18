import org.json.JSONObject
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.stream.Stream
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric

trait TestDataContext {

    static makeTransactions() {
        Long amountOfRandomTransactionsToCreate = Math.abs(new Random().nextInt(100).toLong())
        def transactions = Stream.iterate(0, { n -> n + 1 })
                .limit(amountOfRandomTransactionsToCreate)
                .map({ element -> makeTransaction() })
                .collect()
        return transactions
    }

    static makeTransaction() {
        def description = transactionDescriptions.get(getRandomIntegerInRange(0, transactionDescriptions.size() - 1))
        return [
                id                 : uniqueRandomNumeric(getRandomIntegerInRange(2,4)),
                userId             : getRandomIntegerInRange(1,10),
                executionTime      : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Timestamp.from(Instant.now())),
                amount             : description == "Salary" ? generatePositiveRandomDouble() : generateNegativeRandomDouble(),
                purchaseDescription: description
        ]
    }

    static JSONObject makeJSONActual(Object transaction){
        JSONObject actual = new JSONObject()
        actual.put("id", transaction.id as Long)
        actual.put("userId", transaction.userId as Long)
        actual.put("executionTime", transaction.executionTime)
        actual.put("amount", transaction.amount)
        actual.put("purchaseDescription", transaction.purchaseDescription)
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

    static transactionDescriptions = ["Starbucks", "McDonald's", "Apple Itunes", "Salary", "Amazon", "Walmart", "Papa Joe's"]

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