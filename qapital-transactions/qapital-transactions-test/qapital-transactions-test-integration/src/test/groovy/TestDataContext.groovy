import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.stream.Stream
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric

trait TestDataContext {

    def makeTransactions() {
        Long amountOfRandomTransactionsToCreate = new Random(new Random().nextInt()).nextLong();
        def transactions = Stream.iterate(0, { n -> n + 1 })
                .limit(amountOfRandomTransactionsToCreate)
                .map({ element -> getMaximumNumberOfParameters() })
                .collect()
        return transactions
    }

    static makeTransaction() {
        def description = transactionDescriptions.get(getRandomIntegerInRange(0, transactionDescriptions.size() - 1))
        Integer random =  new Random().nextInt(10 + 1)
        return [
                id                 : uniqueRandomNumeric(random),
                userId             : uniqueRandomNumeric(random),
                executionTime      : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Timestamp.from(Instant.now())),
                amount             : description == "Salary" ? generatePositiveRandomDouble() : generateNegativeRandomDouble(),
                purchaseDescription: description
        ]
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

        return random
    }

    static transactionDescriptions = ["Starbucks", "McDonald's", "Apple Itunes", "Salary", "Amazon", "Walmart", "Papa Joe's"]

    static Integer getRandomIntegerInRange(Integer min, Integer max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    static Double generateNegativeRandomDouble() {
        return -new Random().nextDouble();
    }

    static Double generatePositiveRandomDouble() {
        return new Random().nextDouble();
    }
}