
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import spock.lang.Specification

class IntegrationTestSuite extends Specification implements TestDataContext {

    @Autowired
    TestRestTemplate restTemplate

    static createJsonRequest(requestBody) {
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON);
        String json = new ObjectMapper().writeValueAsString(requestBody);
        return new HttpEntity<>(json, headers);
    }

    static createUriWithPort() { return "http://" + "localhost" + ":" + "8082";
    }
}