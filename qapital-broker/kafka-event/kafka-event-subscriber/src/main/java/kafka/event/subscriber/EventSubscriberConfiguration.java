package kafka.event.subscriber;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.Configurable;
import java.util.Map;
import java.util.Objects;
import static java.util.Objects.isNull;

public class EventSubscriberConfiguration implements Configurable {

    private Map<String,?> properties;
    private static final String TOPIC = "topic";

    @Override
    public void configure(Map<String, ?> configurations) {
        this.properties = Objects.requireNonNull(configurations,
                "Properties must not be null!");
    }

    public Boolean isNotEmpty(){
        return !isNull(properties) && properties.size() > 0;
    }

    public String getBootStrapServers(){
        return String.valueOf(properties.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
    }

    public String getClientId(){
        return String.valueOf(properties.get(ConsumerConfig.CLIENT_ID_CONFIG));
    }

    public String getGroupId(){
        return String.valueOf(properties.get(ConsumerConfig.GROUP_ID_CONFIG));
    }

    public String getTopic(){
        return String.valueOf(properties.get(TOPIC));
    }

    public Long getHeartbeatInterval(){
        return (Long) properties.get(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG);
    }

    public Long getPollTimeOut(){
        return (Long)(properties.get(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG));
    }

    public Long getReconnectBackOffMax(){
        return (Long) properties.get(ConsumerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG);
    }

    public Long getReconnectBackOff(){
        return (Long) properties.get(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG);
    }
}
