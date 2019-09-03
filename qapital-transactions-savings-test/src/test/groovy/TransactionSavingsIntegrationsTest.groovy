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
//
//    @Test
//    def "Should be able to fetch all SavingsGoals GET /savings-goal/{id}/{userId}"() {
//
//        when: "a SavingsGoal is persisted POST /savings-goal"
//
//        def savingsGoal = createSavingsGoal()
//        def userId = savingsGoal.userId
//        def id = savingsGoal.id
//
//        HttpEntity<String> savingsGoalJsonRequest = createJsonRequest(savingsGoal)
//
//        ResponseEntity<String> insertResponse = restTemplate.postForEntity(createUriWithPort() + "/savings-goal", savingsGoalJsonRequest, String)
//
//        assert insertResponse.statusCode == CREATED
//
//        then: "all SavingsEvetns are fetched for user GET /savings-goal/${id}/${userId}"
//
//        ResponseEntity<String> getResponse = restTemplate.getForEntity(createUriWithPort() + "/savings-goal/${id}/${userId}", String)
//
//        assert getResponse.statusCode == OK
//
//        JSONObject actual = makeSavingsGoalJSON(savingsGoal)
//        JSONAssert.assertEquals(getResponse.body, actual,false)
//    }
//
//    @Test
//    def "Should be able to fetch all savingsEvents GET /savings-event/{id}/{userId}"() {
//
//        when: "a SavingsEvent is persisted POST /savings-event"
//
//        def savingsEvent = createSavingsEvent()
//        def userId = savingsEvent.userId
//        def id = savingsEvent.id
//
//        HttpEntity<String> savingsEventJsonRequest = createJsonRequest(savingsEvent)
//
//        ResponseEntity<String> insertResponse = restTemplate.postForEntity(createUriWithPort() + "/savings-event", savingsEventJsonRequest, String)
//
//        assert insertResponse.statusCode == CREATED
//
//        then: "all SavingsEvents are fetched for user GET /savings-event/${id}/${userId}"
//
//        ResponseEntity<String> getResponse = restTemplate.getForEntity(createUriWithPort() + "/savings-event/${id}/${userId}", String)
//
//        assert getResponse.statusCode == OK
//
//        JSONObject actual = makeSavingsEventSON(savingsEvent)
//        JSONAssert.assertEquals(getResponse.body, actual,false)
//    }

    @Test
    def "Should be able to retrieve a SavingsRule for a User GET /savings-rule/{userId}/{id}"() {

        when: "a SavingsRule is persisted POST /savings-rule"

            def savingsRule = createSavingsRule()
            def userId = savingsRule.userId
            def id = savingsRule.id

            HttpEntity<String> savingsRuleJsonRequest = createJsonRequest(savingsRule)

            ResponseEntity<String> insertResponse = restTemplate.postForEntity(createUriWithPort() + "/savings-rule", savingsRuleJsonRequest, String)

            assert insertResponse.statusCode == CREATED

        then: "all savingsRules are fetched for user GET /savings-rule/${userId}/${id}"

            ResponseEntity<String> getResponse = restTemplate.getForEntity(createUriWithPort() + "/savings-rule/${userId}/${id}", String)

            ResponseEntity<String> getResponseUserId = restTemplate.getForEntity(createUriWithPort() + "/savings-rule/${userId}", String)

            assert getResponse.statusCode == OK

            JSONObject actual = makeSavingsRuleJSON(savingsRule)
            JSONAssert.assertEquals(getResponse.body, actual,false)
    }
}

