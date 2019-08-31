package qapital.broker.kafka.event.subscriber;

public interface Initializable<T> {
    T initialize();
}
