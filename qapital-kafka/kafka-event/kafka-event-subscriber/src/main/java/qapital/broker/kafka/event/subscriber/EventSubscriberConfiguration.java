package qapital.broker.kafka.event.subscriber;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.Configurable;
import java.util.Map;
import java.util.Objects;
import static java.util.Objects.isNull;

public class EventSubscriberConfiguration implements Configurable {

    private Map<String,?> configuration;
    private static final String TOPIC = "topic";

    public EventSubscriberConfiguration(Map<String, ?> properties) {
        configure(properties);
    }

    @Override
    public void configure(Map<String, ?> configurations) {
        this.configuration = Objects.requireNonNull(configurations,
                "Properties must not be null!");
    }

    public Boolean isNotEmpty(){
        return !isNull(configuration) && configuration.size() > 0;
    }

    public String getBootStrapServers(){
        return String.valueOf(configuration.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
    }

    public String getClientId(){
        return String.valueOf(configuration.get(ConsumerConfig.CLIENT_ID_CONFIG));
    }

    public String getGroupId(){
        return String.valueOf(configuration.get(ConsumerConfig.GROUP_ID_CONFIG));
    }

    public String getPollMaxDuration(){
        return String.valueOf(configuration.get(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG));
    }

    public String getTopic(){
        return String.valueOf(configuration.get(TOPIC));
    }

}
