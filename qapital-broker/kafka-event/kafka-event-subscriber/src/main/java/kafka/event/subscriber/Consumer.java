package kafka.event.subscriber;

import com.qapital.broker.kafka.event.EventWrapperOuterClass;
import kafka.event.serialization.EventWrapperDeserializer;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

@Service
public class Consumer{

    private final Deserializer<String> keyDeserializer;

    private final Deserializer<EventWrapperOuterClass.EventWrapper> valueDeserializer;

    private KafkaConsumer<String, EventWrapperOuterClass.EventWrapper> kafkaConsumer;

    public Consumer(
            String bootstrapServers,
            String clientId,
            String groupId,
            Long heartbeatInterval,
            Long reconnectBackOffMaxMs,
            Long reconnectBackOff,
            String topic,
            StringDeserializer keyDeserializer,
            EventWrapperDeserializer valueDeserializer) {
        this.keyDeserializer = Objects.requireNonNull(keyDeserializer, "keyDeserializer");
        this.valueDeserializer = Objects.requireNonNull(valueDeserializer, "valueDeserializer");

        init(bootstrapServers, clientId, groupId,heartbeatInterval,reconnectBackOffMaxMs, reconnectBackOff, topic);
    }

    public void init(String bootStrapServers,
                     String clientId,
                     String groupId,
                     Long heartBeatInterval,
                     Long reconnectBackOffMaxMs,
                     Long reconnectBackOff,
                     String topic) {
        Properties configProperties = new Properties();
        configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        configProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
        configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configProperties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, heartBeatInterval);
        configProperties.put(ConsumerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG, reconnectBackOffMaxMs);
        configProperties.put(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG, reconnectBackOff);
        this.kafkaConsumer = new KafkaConsumer<>(configProperties, this.keyDeserializer, this.valueDeserializer);
        this.kafkaConsumer.subscribe(Collections.singletonList(topic));
    }
}
