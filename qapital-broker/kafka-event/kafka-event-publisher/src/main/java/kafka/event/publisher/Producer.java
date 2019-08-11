package kafka.event.publisher;

import com.google.common.base.MoreObjects;
import com.qapital.broker.kafka.event.EventWrapperOuterClass;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


/**
 * A producers that emits messages to a Kafka broker.
 * <p>
 * Kafka works in a partitioned mode. Messages has to be assigned to a partition by the client when they are
 * sent to the broker.
 * <p>
 * In the Kafka API there are 4 ways a messages can be assigned to a partition,
 * <p>
 * Manual Mode: When you create a ProducerRecord, use the overloaded constructor new
 * ProducerRecord(topicName, partitionId,messageKey,message) to specify a partition id.
 * <p>
 * Hashing mode (Locality sensitive): When you create a ProducerRecord, specify a messageKey, by calling
 * new ProducerRecord(topicName,messageKey,message).
 * <p>
 * Default Mode: DefaultPartitioner will use the hash of the key to ensure that all messages for the same key go
 * to same producer. This is the easiest and most common approach.
 * <p>
 * Spraying Mode(Random Load Balancing): If you don't want to control which partition messages go to, simply call
 * new ProducerRecord(topicName, message) to create your ProducerRecord. In this case the partitioner will publish
 * messages to all the partitions in round-robin fashion, ensuring a balanced server load.
 *
 */

public class Producer implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private KafkaProducer<String, EventWrapperOuterClass.EventWrapper> producer;

    private final String topic;

    public Producer(String bootstrapServers,
                    String clientId,
                    String topic,
                    Integer batchSize,
                    Integer reConnectBackOffMax,
                    Integer reConnectBackOff,
                    Serializer<String> keySerializer,
                    Serializer<EventWrapperOuterClass.EventWrapper> valueSerializer) {
        this.topic = Objects.requireNonNull(topic, "topic");

        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProperties.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        configProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        configProperties.put(ProducerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG, reConnectBackOffMax);
        configProperties.put(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG, reConnectBackOff);
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

    /**
     * Sends the message to the broker using a random routing value
     *
     * @param value
     */
    public void publish(EventWrapperOuterClass.EventWrapper value) {
        producer.send(new ProducerRecord<String, EventWrapperOuterClass.EventWrapper>(this.topic, value));
    }

    /**
     * Sends the message to the broker using the mod(key,numberOfPartitions)
     *
     * @param key
     * @param value
     */
    public void publish(String key, EventWrapperOuterClass.EventWrapper value) {
        producer.send(new ProducerRecord<>(topic, key, value));
    }

    /**
     * Sends the message to the broker using the provided partition id
     *
     * @param partition
     * @param value
     */
    public void publish(Integer partition, EventWrapperOuterClass.EventWrapper value) {
        producer.send(new ProducerRecord<>(topic, partition, null, value));
    }

    /**
     * Sends the message to the broker using the provided partition id and key
     *
     * @param partition
     * @param key
     * @param value
     */
    public void publish(Integer partition, String key, EventWrapperOuterClass.EventWrapper value) {
        producer.send(new ProducerRecord<>(topic, partition, key, value));
    }

}
