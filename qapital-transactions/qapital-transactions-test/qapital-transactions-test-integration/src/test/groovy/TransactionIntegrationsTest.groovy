
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner
import java.sql.Timestamp
import static org.springframework.http.HttpStatus.CREATED
import qapital.transactions.main.TransactionsApp
import static org.springframework.http.HttpStatus.OK

@SpringBootTest(
        classes = TransactionsApp.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
class TransactionIntegrationsTest extends IntegrationTestSuite {

    @Test
    def "Should be able to fetch all transactions GET /transactions"() {

        when:"a transaction is persisted"

            def transaction = makeTransaction()

            HttpEntity<String> transactionJsonRequest = createJsonRequest(transaction)

            ResponseEntity<String> insertResponse = restTemplate.postForEntity(createUriWithPort()+ "/transactions", transactionJsonRequest, String)

            assert insertResponse.statusCode == CREATED

        then:"all transactions are fetched"

            ResponseEntity<String> getResponse =
                    restTemplate.getForEntity(createUriWithPort()+ "/transactions", String)

             assert getResponse.statusCode == OK
    }

    @Test
    def "Should be able to fetch all transactions for userId GET /transactions/{userId}"() {

        when:"a transaction is persisted"

            def transaction = makeTransaction()

            def userId = transaction.userId

            HttpEntity<String> transactionJsonRequest = createJsonRequest(transaction)

            ResponseEntity<String> insertResponse =
                restTemplate.postForEntity(createUriWithPort()+ "/transactions", transactionJsonRequest, String)

            assert insertResponse.statusCode == CREATED

        then:"all transactions are fetched for ${userId}"

            ResponseEntity<List> getResponse =
                restTemplate.getForEntity(createUriWithPort()+ "/transactions/${userId}", List)

            assert getResponse.statusCode == OK
            assert getResponse.body.userId.get(0) == userId as Integer
            assert getResponse.body.id.get(0) == transaction.id as Integer
            assert getResponse.body.purchaseDescription.get(0) == transaction.purchaseDescription as String
            assert getResponse.body.executionTime.get(0) == transaction.executionTime as String
            assert getResponse.body.amount.get(0) == transaction.amount as Double
    }
}