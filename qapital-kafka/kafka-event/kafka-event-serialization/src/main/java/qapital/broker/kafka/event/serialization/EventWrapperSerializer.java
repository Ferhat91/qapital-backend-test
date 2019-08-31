package qapital.broker.kafka.event.serialization;

import com.qapital.broker.kafka.event.EventWrapperOuterClass;
import org.apache.kafka.common.serialization.Serializer;
import java.util.Map;

public class EventWrapperSerializer implements Serializer<EventWrapperOuterClass.EventWrapper> {

    private qapital.broker.kafka.event.serialization.Serializer serializer = new ProtobufSerializer();

    @Override
    public byte[] serialize(final String topic, final EventWrapperOuterClass.EventWrapper data) {
        return serializer.serialize(data);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public void close() {

    }
}

