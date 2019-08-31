package qapital.broker.kafka.event.subscriber;

import java.util.Objects;

public class KafkaEventSubscriber implements Initializable<KafkaEventSubscriber> {

    private EventSubscriberConfiguration eventSubscriberConfiguration;

    private Consumer consumer;

    private ConsumerListener consumerListener;

    public KafkaEventSubscriber(EventSubscriberConfiguration eventSubscriberConfiguration, ConsumerListener consumerListener) {
        this.eventSubscriberConfiguration = Objects.requireNonNull(eventSubscriberConfiguration, "kafkaSubscriberConfigurations");
        this.consumerListener = Objects.requireNonNull(consumerListener, "consumerListener");
        this.consumer = new Consumer(this.eventSubscriberConfiguration, this.consumerListener);
    }

    public EventSubscriberConfiguration getEventSubscriberConfiguration() {
        return eventSubscriberConfiguration;
    }

    @Override
    public KafkaEventSubscriber initialize() {
        this.consumer.run();
        return this;
    }
}
