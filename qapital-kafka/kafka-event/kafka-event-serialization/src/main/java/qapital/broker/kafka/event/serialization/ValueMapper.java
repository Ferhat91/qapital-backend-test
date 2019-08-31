package qapital.broker.kafka.event.serialization;

import com.google.protobuf.*;
import static java.util.Objects.isNull;

public class ValueMapper {

    public static String map(StringValue stringValue){
        return !isNull(stringValue) ? stringValue.getValue() : null;
    }

    public static Double map(DoubleValue doubleValue){
        return !isNull(doubleValue) ? doubleValue.getValue() : null;
    }

    public static Long map(Int64Value longValue){
        return !isNull(longValue) ? longValue.getValue() : null;
    }

    public static Boolean map(BoolValue boolValue){
        return !isNull(boolValue) ? boolValue.getValue() : null;
    }

    public static Integer map(Int32Value integerValue){
        return !isNull(integerValue) ? integerValue.getValue() : null;
    }

    public static StringValue map(String string){
        return !isNull(string) ? StringValue.of(string) : StringValue.newBuilder().build();
    }

    public static DoubleValue map(Double doubleValue){
        return !isNull(doubleValue) ? DoubleValue.of(doubleValue) : DoubleValue.newBuilder().build();
    }

    public static Int64Value map(Long longValue){
        return !isNull(longValue) ? Int64Value.of(longValue) : Int64Value.newBuilder().build();
    }

    public static BoolValue map(Boolean boolValue){
        return !isNull(boolValue) ? BoolValue.of(boolValue) : BoolValue.newBuilder().build();
    }

    public static Int32Value map(Integer integerValue){
        return !isNull(integerValue) ? Int32Value.of(integerValue) : Int32Value.newBuilder().build();
    }
}
