package qapital.broker.kafka.event.serialization;


import com.qapital.broker.kafka.event.EventWrapperOuterClass;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

/**
 * {@code EventWrapperDeserializer} receives the events being sent over Kafka and deserialize it to a
 * {@code EventWrapperOuterClass.EventWrapper}.
 */
public class EventWrapperDeserializer implements Deserializer<EventWrapperOuterClass.EventWrapper> {

    private Serializer serializer = new ProtobufSerializer();

    @Override
    public EventWrapperOuterClass.EventWrapper deserialize(final String topic, byte[] data) {
        return serializer.deserialize(EventWrapperOuterClass.EventWrapper.class, data);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public void close() {
    }
}