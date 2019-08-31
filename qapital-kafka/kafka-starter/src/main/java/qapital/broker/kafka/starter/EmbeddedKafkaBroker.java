package qapital.broker.kafka.starter;

import kafka.server.KafkaConfig;
import kafka.server.KafkaServerStartable;
import java.io.File;
import java.util.Properties;
import java.util.UUID;

public class EmbeddedKafkaBroker implements AutoCloseable {

    private Properties kafkaBrokerConfig = new Properties();

    private KafkaServerStartable broker;

    private String zookeeperConnectionString;

    private Integer kafkaPort;

    public EmbeddedKafkaBroker() {
        this(9092, 2185);
    }

    public EmbeddedKafkaBroker(Integer kafkaPort, Integer zookeeperPort) {
        this.kafkaPort = kafkaPort;
        this.zookeeperConnectionString = "localhost:" + zookeeperPort;
    }

    public void startup() {
        File logDirectory = new File(System.getProperty("java.io.tmpdir"), "kafka_log-" + UUID.randomUUID());
        setKafkaBrokerProperties(logDirectory);
        broker = new KafkaServerStartable(new KafkaConfig(kafkaBrokerConfig));
        broker.startup();
    }

    private void setKafkaBrokerProperties(File logDirectory){
        kafkaBrokerConfig.setProperty("zookeeper.connect", zookeeperConnectionString);
        kafkaBrokerConfig.setProperty("host.name", "localhost");
        kafkaBrokerConfig.setProperty("port", Integer.toString(kafkaPort));
        kafkaBrokerConfig.setProperty("log.dir", logDirectory.getAbsolutePath());
        kafkaBrokerConfig.setProperty("log.flush.interval.messages", String.valueOf(1));
        kafkaBrokerConfig.setProperty("auto.create.topics.enable", "true");
        kafkaBrokerConfig.setProperty("offsets.topic.replication.factor", "1"); // Disable replication
        kafkaBrokerConfig.setProperty("group.initial.rebalance.delay.ms", "0"); // Disable rebalance delay
        kafkaBrokerConfig.setProperty("num.partitions", "1"); //This setting is overridden by TopicFactory
    }

    @Override
    public void close() {
        if (broker != null) {
            broker.shutdown();
            broker.awaitShutdown();
        }
    }
}
