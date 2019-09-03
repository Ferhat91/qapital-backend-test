import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner
import qapital.broker.kafka.starter.KafkaServerApplication
import static org.springframework.http.HttpStatus.CREATED
import qapital.transactions.main.TransactionsApplication
import static org.springframework.http.HttpStatus.OK

@SpringBootTest(
        classes = [KafkaServerApplication.class,
                TransactionsApplication.class],
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
class TransactionIntegrationsTest extends IntegrationTestSuite {

    @Test
    def "Should be able to fetch all transactions GET /transactions"() {

        when: "a transaction is persisted"

        def transaction = makeTransaction()

        HttpEntity<String> transactionJsonRequest = createJsonRequest(transaction)

        ResponseEntity<String> insertResponse = restTemplate.postForEntity(createUriWithPort() + "/transactions", transactionJsonRequest, String)

        assert insertResponse.statusCode == CREATED

        then: "all transactions are fetched"

        ResponseEntity<String> getResponse =
                restTemplate.getForEntity(createUriWithPort() + "/transactions", String)

        assert getResponse.statusCode == OK
    }

    @Test
    def "Should be able to fetch all transactions for userId GET /transactions/{userId}/{transactionId}"() {

        when: "a transaction is persisted"

            def transaction = makeTransaction()
            def userId = transaction.userId
            def transactionId = transaction.id

            HttpEntity<String> transactionJsonRequest = createJsonRequest(transaction)

            ResponseEntity<String> insertResponse =
                restTemplate.postForEntity(createUriWithPort() + "/transactions", transactionJsonRequest, String)

            assert insertResponse.statusCode == CREATED

        then:
            "all transactions are fetched for ${userId}"

            ResponseEntity<String> getResponse = restTemplate.getForEntity(createUriWithPort() + "/transactions/${userId}/${transactionId}", String)

            assert getResponse.statusCode == OK
            JSONObject actual = makeJSONActual(transaction)
            JSONAssert.assertEquals(getResponse.body, actual, false)
    }

}