package kafka.starter;

import kafka.event.publisher.EventPublisherConfiguration;
import kafka.event.publisher.KafkaEventPublisher;
import kafka.event.serialization.ProtobufSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaRestService {

    private final KafkaEventPublisher kafkaEventPublisher =
            new KafkaEventPublisher(new EventPublisherConfiguration(), new ProtobufSerializer());

    @Autowired
    KafkaRestService(KafkaEventPublisher kafkaEventPublisher) {
        this.kafkaEventPublisher.initialize();
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(Object event) {
        this.kafkaEventPublisher.publish(event);
    }
}


