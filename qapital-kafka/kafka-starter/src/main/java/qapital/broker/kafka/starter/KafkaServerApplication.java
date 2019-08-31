package qapital.broker.kafka.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:qapital-broker-kafka-starter.xml"})
public class KafkaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaServerApplication.class, args);
    }
}
