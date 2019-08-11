package kafka.test.unit;

import com.qapital.broker.kafka.event.EventWrapperOuterClass;
import kafka.event.publisher.KafkaEventPublisher;
import kafka.event.serialization.ProtobufSerializer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class KafkaEventPublisherUnitTest {

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, "topic");

    @Test
    public void publisherTest() {
        EventWrapperOuterClass.EventWrapper.newBuilder().build();
        eventPublisher.publish(EventWrapperOuterClass.EventWrapper.newBuilder().build());
    }
}
