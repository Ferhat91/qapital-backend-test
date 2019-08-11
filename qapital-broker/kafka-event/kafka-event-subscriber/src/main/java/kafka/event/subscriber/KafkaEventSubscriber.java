package kafka.event.subscriber;

import java.util.Objects;

public class KafkaEventSubscriber {

    private EventSubscriberConfiguration eventSubscriberConfiguration;

    private Consumer consumer;

    public KafkaEventSubscriber(EventSubscriberConfiguration eventSubscriberConfiguration, Consumer consumer) {
        this.eventSubscriberConfiguration = Objects.requireNonNull(eventSubscriberConfiguration, "configurations");
        this.consumer = Objects.requireNonNull(consumer, "consumer");
    }

    public void connect() {
        this.consumer.init(eventSubscriberConfiguration.getBootStrapServers(),
                eventSubscriberConfiguration.getClientId(),
                eventSubscriberConfiguration.getGroupId(),
                eventSubscriberConfiguration.getHeartbeatInterval(),
                eventSubscriberConfiguration.getReconnectBackOffMax(),
                eventSubscriberConfiguration.getReconnectBackOff(),
                eventSubscriberConfiguration.getTopic());
    }
}
