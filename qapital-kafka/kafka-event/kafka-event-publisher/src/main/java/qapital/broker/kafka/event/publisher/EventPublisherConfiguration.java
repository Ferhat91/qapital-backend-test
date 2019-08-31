package qapital.broker.kafka.event.publisher;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.Configurable;
import java.util.Map;
import java.util.Objects;
import static java.util.Objects.isNull;

public class EventPublisherConfiguration implements Configurable {

    private static final String TOPIC = "topic";
    private Map<String,?> properties;

    public EventPublisherConfiguration(Map<String, ?> properties) {
        configure(properties);
    }

    @Override
    public void configure(Map<String, ?> configurations) {
        this.properties = Objects.requireNonNull(configurations, "kafkaConsumerConfigurations");
    }

    public Boolean isNotEmpty(){
        return !isNull(properties) && properties.size() > 0;
    }

    public String getBootStrapServers(){
        return String.valueOf(properties.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
    }

    public String getClientId(){
        return String.valueOf(properties.get(ProducerConfig.CLIENT_ID_CONFIG));
    }

    public String getTopic(){
        return String.valueOf(properties.get(TOPIC));
    }

    public String getProducerKeySerializer(){
        return String.valueOf(properties.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
    }
    public String getProducerKeyValueSerializer(){
        return String.valueOf(properties.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
    }

    public Integer getBatchSize(){
        return (Integer) properties.get(ProducerConfig.BATCH_SIZE_CONFIG);
    }

    public Integer getReConnectBackOffMax(){
        return (Integer)properties.get(ProducerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG);
    }

    public Integer getReConnectBackOff(){
        return (Integer)properties.get(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG);
    }
}
