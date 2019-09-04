package qapital.broker.kafka.event.subscriber;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class KafkaEventSubscriber implements AutoCloseable, Initializable<KafkaEventSubscriber>{

    private EventSubscriberConfiguration eventSubscriberConfiguration;

    private Consumer consumer;

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

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
        periodicConsume();
        return this;
    }

    @Override
    public void close() {
        this.consumer.shutdown();
    }

    private void periodicConsume(){
        executor.scheduleAtFixedRate(this.consumer, 0, 2, TimeUnit.SECONDS);
    }
}
