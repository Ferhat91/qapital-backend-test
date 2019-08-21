package qapital.broker.kafka.starter;

import java.io.File;
import java.util.Properties;

public class EmbeddedKafkaBroker implements AutoCloseable {

    private String zookeeperConnectionString;

    private Integer kafkaPort;

    private Properties kafkaBrokerConfig = new Properties();

    private KafkaServerStartable broker;

    private File logDirectory;

    @Override
    public void close() throws Exception {

    }
}
