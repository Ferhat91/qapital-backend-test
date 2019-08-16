

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner
import static org.springframework.http.HttpStatus.CREATED
import qapital.transactions.main.TransactionsApp
import static org.springframework.http.HttpStatus.OK

@SpringBootTest(
        classes = TransactionsApp.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureBefore
class TransactionIntegrationsTest extends IntegrationTestSuite {

    @Test
    def "Should be able to fetch all transactions GET /transactions"() {

        when:"a transaction is persisted"

            HttpEntity<String> transactionJsonRequest = createJsonRequest(transaction)

            ResponseEntity<String> insertResponse =
                    restTemplate.postForEntity(createUriWithPort()+ "/transactions", transactionJsonRequest, String)

            assert insertResponse.statusCode == CREATED

        then:"all transactions are fetched"

            ResponseEntity<String> getResponse =
                    restTemplate.getForEntity(createUriWithPort()+ "/transactions", String.class)

             assert getResponse.statusCode == OK
    }

    @Test
    def "Should be able to fetch all transactions for userId GET /transactions/{userId}"() {

        when:"a transaction is persisted"

            def transaction = transaction
            def userId = transaction.userId

            HttpEntity<String> transactionJsonRequest = createJsonRequest(transaction)

            ResponseEntity<String> insertResponse =
                restTemplate.postForEntity(createUriWithPort()+ "/transactions", transactionJsonRequest, String)

            assert insertResponse.statusCode == CREATED

        then:"all transactions are fetched for ${userId}"

            ResponseEntity<List> getResponse =
                restTemplate.getForEntity(createUriWithPort()+ "/transactions/${userId}", List)

            assert getResponse.statusCode == OK
            assert getResponse.body.userId.get(0) == userId
            assert getResponse.body.id.get(0) == transaction.id
            assert getResponse.body.purchaseDescription.get(0) == transaction.purchaseDescription
            assert getResponse.body.executionTime.get(0) == transaction.executionTime
            assert getResponse.body.amount.get(0) == transaction.amount
    }
}