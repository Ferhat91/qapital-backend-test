package kafka.event.serialization;

/**
 * An interface that is used by the event framework to serialize an event back and forth to an byte[] array
 ***/

public interface Serializer {

    byte[] serialize(Object t);

    <T> T deserialize(Class<?> javaType, byte[] data);

    <T> T deserialize(String javaType, byte[] data);
}
