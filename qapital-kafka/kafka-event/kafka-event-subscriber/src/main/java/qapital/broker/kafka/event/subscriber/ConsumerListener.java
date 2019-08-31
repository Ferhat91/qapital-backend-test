package qapital.broker.kafka.event.subscriber;

import com.qapital.broker.kafka.event.EventWrapperOuterClass;

/**
 * A Kafka consumer that connects to a list of bootstrap hosts and can be configured with a
 * key and value deserializer of choice.
 *
 * A Kafka consumer will automatically assign itself to all partitions if it is the only consumer.
 *
 * When another consumer attaches to the same topic the load will be spread evenly across both
 * consumers.
 **/
public interface ConsumerListener {

    void onNext(EventWrapperOuterClass.EventWrapper message);

    void onError(Throwable error);

    void onClose();
}