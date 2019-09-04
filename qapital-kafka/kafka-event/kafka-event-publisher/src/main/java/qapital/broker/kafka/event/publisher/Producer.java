package qapital.broker.kafka.event.publisher;

import com.qapital.broker.kafka.event.EventWrapperOuterClass;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Producer implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private KafkaProducer<String, EventWrapperOuterClass.EventWrapper> producer;

    private final String topic;

    public Producer(String bootstrapServers,
                    String topic,
                    String clientId,
                    Serializer<String> keySerializer,
                    Serializer<EventWrapperOuterClass.EventWrapper> valueSerializer) {
        this.topic = Objects.requireNonNull(topic, "topic");
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProperties.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        this.producer = new KafkaProducer<>(configProperties, keySerializer, valueSerializer);

    }

    @Override
    public void close() {
        if (producer != null) {
            try {
                LOGGER.info("Closing producer: timeout={}, unit={}", Long.MAX_VALUE, TimeUnit.MILLISECONDS);
                producer.close();
            } catch (Exception exception) {
                LOGGER.error("Failed closing producer, which might lead to memory leaks and unexpected behavior", exception);
            }
        }
    }

    public void publish(EventWrapperOuterClass.EventWrapper value) {
        producer.send(new ProducerRecord<>(this.topic, value));
    }
}
