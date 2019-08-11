package kafka.starter;

import kafka.event.publisher.EventPublisherConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean("kafkaConfig")
    public static EventPublisherConfiguration getEventPublisherConfiguration(){
        Map<String, ?> setConfig = new HashMap<>();
        EventPublisherConfiguration configuration =  new EventPublisherConfiguration();
        configuration.configure(setConfig);
        return configuration;
    }
}
