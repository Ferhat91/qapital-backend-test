package qapital.broker.kafka.event.publisher;

import com.google.protobuf.ByteString;
import com.qapital.broker.kafka.event.EventWrapperOuterClass;
import qapital.broker.kafka.event.serialization.EventWrapperSerializer;
import qapital.broker.kafka.event.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;
import static java.util.Objects.isNull;

public class KafkaEventPublisher implements EventPublisher, AutoCloseable, Initializable<KafkaEventPublisher> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaEventPublisher.class);

    private EventPublisherConfiguration eventPublisherConfiguration;

    private Producer producer;

    private Serializer serializer;

    public KafkaEventPublisher(EventPublisherConfiguration eventPublisherConfiguration, Serializer serializer){
        this.eventPublisherConfiguration = eventPublisherConfiguration;
        this.serializer = Objects.requireNonNull(serializer, "serializer");
    }

    @Override
    public KafkaEventPublisher initialize() {
        if (producer != null) {
            throw new IllegalStateException("The producer has already been initialized");
        }
        if(eventPublisherConfiguration.isNotEmpty() && !isNull(eventPublisherConfiguration.getTopic())
                && !isNull(eventPublisherConfiguration.getTopic())
                && !isNull(eventPublisherConfiguration.getClientId())){
            producer = new Producer(
                    eventPublisherConfiguration.getBootStrapServers(),
                    eventPublisherConfiguration.getTopic(),
                    eventPublisherConfiguration.getClientId(),
                    new StringSerializer(),
                    new EventWrapperSerializer());
        }
        return this;
    }

    @Override
    public void close() {
        destroy();
    }

    private void destroy() {
        if (producer != null) {
            producer.close();
        }
    }

    @Override
    public void publish(Object event) {
        if (isNull(producer)) {
            LOGGER.error("Producer is null! Did you forget to call init on the publisher?");
        }
        EventWrapperOuterClass.EventWrapper eventWrapper = null;
        try {
            eventWrapper = EventWrapperOuterClass.EventWrapper.newBuilder()
                    .setData(ByteString.copyFrom(serializer.serialize(event)))
                    .setType(event.getClass().getTypeName())
                    .setRequestContext(ByteString.EMPTY)
                    .build();
        } catch (Exception e) {
            LOGGER.error("Failed serializing event", e);
            return;
        }

        try {
            LOGGER.info(String.format("producer=%s is succesfully publishing the event!", producer));
            producer.publish(eventWrapper);
        } catch (Exception e) {
            LOGGER.error(String.format("Failed sending event: producer=%s", producer), e);
        }
    }
}

