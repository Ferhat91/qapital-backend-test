package qapital.broker.kafka.event.subscriber;

import com.qapital.broker.kafka.event.EventWrapperOuterClass;
import org.apache.kafka.common.errors.InterruptException;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qapital.broker.kafka.event.serialization.EventWrapperDeserializer;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class Consumer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    private final AtomicBoolean running = new AtomicBoolean(true);

    private EventSubscriberConfiguration eventSubscriberConfiguration;

    private ConsumerListener listener;

    private final StringDeserializer keyDeserializer = new StringDeserializer();

    private final EventWrapperDeserializer valueDeserializer = new EventWrapperDeserializer();

    private KafkaConsumer<String, EventWrapperOuterClass.EventWrapper> kafkaConsumer;

    public Consumer(EventSubscriberConfiguration eventSubscriberConfiguration, ConsumerListener listener) {
        this.eventSubscriberConfiguration = Objects.requireNonNull(eventSubscriberConfiguration, "eventSubscriberConfiguration");
        this.listener = Objects.requireNonNull(listener);
    }

    public KafkaConsumer<String, EventWrapperOuterClass.EventWrapper> init() {
        Properties configProperties = new Properties();
        configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, eventSubscriberConfiguration.getBootStrapServers());
        configProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, eventSubscriberConfiguration.getClientId());
        configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, eventSubscriberConfiguration.getGroupId());
        this.kafkaConsumer = new KafkaConsumer<>(configProperties, this.keyDeserializer, this.valueDeserializer);
        this.kafkaConsumer.subscribe(Collections.singletonList(eventSubscriberConfiguration.getTopic()));
        return this.kafkaConsumer;
}

    @Override
    public void run() {
        try {
            init();
            while (running.get()) {

                ConsumerRecords<String, EventWrapperOuterClass.EventWrapper> records = kafkaConsumer.poll(Duration.ofMillis(Long.valueOf(eventSubscriberConfiguration.getPollMaxDuration())));
                for (ConsumerRecord<String, EventWrapperOuterClass.EventWrapper> record : records) {
                    listener.onNext(record.value());
                }
            }
        } catch (WakeupException exception) {
            // Ignore exception if closing
            if (running.get()) {
                throw exception;
            }
        } catch (InterruptException exception) {
            LOGGER.info("Consumer was interrupted. Closing consumer polling thread. Resetting the interrupt flag!");
            Thread.currentThread().interrupt();
        } catch (Throwable throwable) {
            LOGGER.error("Consumer received an unexpected exception. Will notify an error to subscribers and then terminate");
            listener.onError(throwable);
        } finally {
            listener.onClose();
            kafkaConsumer.close();
        }
    }
}
