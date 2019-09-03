import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner
import qapital.broker.kafka.starter.KafkaServerApplication
import qapital.savings.main.SavingsApplication
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK

@SpringBootTest(
        classes = [KafkaServerApplication.class,
                SavingsApplication.class],
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
class SavingsIntegrationsTest extends IntegrationTestSuite {


    @Test
    def "Should be able to fetch a savingsTransfer GET /savings-transfer/{id}/{userId}"() {

        when: "a savingsTransfer is persisted POST /savings-transfer"

            def savingsTransfer = createSavingsTransfer()
            def userId = savingsTransfer.userId
            def id = savingsTransfer.id

            HttpEntity<String> savingsTransferJsonRequest = createJsonRequest(savingsTransfer)

            ResponseEntity<String> insertResponse = restTemplate.postForEntity(createUriWithPort() + "/savings-transfer", savingsTransferJsonRequest, String)

            assert insertResponse.statusCode == CREATED

        then: "the savingsTransfer is fetched for user GET /savings-transfer/${id}/${userId}"

            ResponseEntity<String> getResponse = restTemplate.getForEntity(createUriWithPort() + "/savings-transfer/${id}/${userId}", String)

            assert getResponse.statusCode == OK

            JSONObject actual = makeSavingsTransferJson(savingsTransfer)
            JSONAssert.assertEquals(getResponse.body, actual,false)
    }

    @Test
    def "Should be able to fetch savingsGoal GET /savings-goal/{id}/{userId}"() {

        when: "a savingsGoal is persisted POST /savings-goal"

        def savingsGoal = createSavingsGoal()
        def userId = savingsGoal.userId
        def id = savingsGoal.id

        HttpEntity<String> savingsGoalJsonRequest = createJsonRequest(savingsGoal)

        ResponseEntity<String> insertResponse = restTemplate.postForEntity(createUriWithPort() + "/savings-goal", savingsGoalJsonRequest, String)

        assert insertResponse.statusCode == CREATED

        then: "the avingsEvetns is fetched for user GET /savings-goal/${id}/${userId}"

        ResponseEntity<String> getResponse = restTemplate.getForEntity(createUriWithPort() + "/savings-goal/${id}/${userId}", String)

        assert getResponse.statusCode == OK

        JSONObject actual = makeSavingsGoalJSON(savingsGoal)
        JSONAssert.assertEquals(getResponse.body, actual,false)
    }

    @Test
    def "Should be able to fetch savingsEvent GET /savings-event/{id}/{userId}"() {

        when: "a savingsEvent is persisted POST /savings-event"

        def savingsEvent = createSavingsEvent()
        def userId = savingsEvent.userId
        def id = savingsEvent.id

        HttpEntity<String> savingsEventJsonRequest = createJsonRequest(savingsEvent)

        ResponseEntity<String> insertResponse = restTemplate.postForEntity(createUriWithPort() + "/savings-event", savingsEventJsonRequest, String)

        assert insertResponse.statusCode == CREATED

        then: "the savingsEvent is fetched for user GET /savings-event/${id}/${userId}"

        ResponseEntity<String> getResponse = restTemplate.getForEntity(createUriWithPort() + "/savings-event/${id}/${userId}", String)

        assert getResponse.statusCode == OK

        JSONObject actual = makeSavingsEventSON(savingsEvent)
        JSONAssert.assertEquals(getResponse.body, actual,false)
    }

    @Test
    def "Should be able to fetch a savingsRule for a user GET /savings-rule/{userId}/{id}"() {

        when: "a savingsRule is persisted POST /savings-rule"

            def savingsRule = createSavingsRule()
            def userId = savingsRule.userId
            def id = savingsRule.id

            HttpEntity<String> savingsRuleJsonRequest = createJsonRequest(savingsRule)

            ResponseEntity<String> insertResponse = restTemplate.postForEntity(createUriWithPort() + "/savings-rule", savingsRuleJsonRequest, String)

            assert insertResponse.statusCode == CREATED

        then: "the savingsRule is fetched for user GET /savings-rule/${userId}/${id}"

            ResponseEntity<String> getResponse = restTemplate.getForEntity(createUriWithPort() + "/savings-rule/${userId}/${id}", String)

            assert getResponse.statusCode == OK

            JSONObject actual = makeSavingsRuleJSON(savingsRule)
            JSONAssert.assertEquals(getResponse.body, actual,false)
    }
}

