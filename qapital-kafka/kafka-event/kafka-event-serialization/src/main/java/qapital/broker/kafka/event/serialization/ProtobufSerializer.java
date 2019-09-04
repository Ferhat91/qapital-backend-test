package qapital.broker.kafka.event.serialization;

import java.lang.reflect.Method;
import java.util.Objects;

public class ProtobufSerializer implements Serializer {

    //Method used by Protobuf to serialize from byte array to object
    private static final String TO_BYTE_ARRAY = "toByteArray";

    //Method used by Protobuf to serialize from object to byte array
    private static final String PARSE_FROM = "parseFrom";

    @Override
    public byte[] serialize(Object object) {
        Objects.requireNonNull(object, "object cannot be null");
        try {
            Method method = object.getClass().getMethod(TO_BYTE_ARRAY);
            return (byte[]) method.invoke(object);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Could not serialize type: type=%s", object.getClass().getSimpleName()));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(Class<?> javaType, byte[] data) {
        try {
            Objects.requireNonNull(javaType, "javaType cannot be null");
            Method method = javaType.getMethod(PARSE_FROM, new Class[]{byte[].class});
            return (T) javaType.cast(method.invoke(null, data));
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Type has no parseFrom method: type=%s", javaType));
        }
    }

    @Override
    public <T> T deserialize(String javaType, byte[] data) {
        Objects.requireNonNull(javaType, "javaType cannot be null");
        try {
            return deserialize(Class.forName(javaType), data);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(String.format("Type could not be found: type=%s. Check that the type is available on the classpath.", javaType));
        }
    }

}
