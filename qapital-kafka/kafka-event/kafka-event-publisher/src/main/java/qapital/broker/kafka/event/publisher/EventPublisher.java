package qapital.broker.kafka.event.publisher;

public interface EventPublisher {

    void publish(Object event);
}
