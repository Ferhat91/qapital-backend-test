package kafka.starter;

import kafka.event.publisher.EventPublisherConfiguration;
import kafka.event.publisher.KafkaEventPublisher;
import kafka.event.serialization.ProtobufSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class KafkaRestService {

    private final KafkaEventPublisher kafkaEventPublisher =
            new KafkaEventPublisher(new EventPublisherConfiguration(), new ProtobufSerializer());

    @Autowired
    KafkaRestService(KafkaEventPublisher kafkaEventPublisher) {
        this.kafkaEventPublisher.initialize();
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic() {
        this.kafkaEventPublisher.initialize();
    }

}


