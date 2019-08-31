package qapital.broker.kafka.event.publisher;

public interface Initializable<T> {
    T initialize();
}
