package qapital.broker.kafka.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbeddedKafkaRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedKafkaRunner.class);

    private EmbeddedZookeeper embeddedZookeeper;

    private EmbeddedKafkaBroker embeddedKafkaBroker;

    public Integer kafkaPort;

    public Integer zookeeperPort;

    public EmbeddedKafkaRunner(Integer kafkaPort, Integer zookeeperPort) {
        this.kafkaPort = kafkaPort;
        this.zookeeperPort = zookeeperPort;
    }

    public EmbeddedKafkaRunner start() {
        LOGGER.info("Starting embedded Zookeeper on port {}", zookeeperPort);
        embeddedZookeeper = new EmbeddedZookeeper(zookeeperPort);
        embeddedZookeeper.startup();

        LOGGER.info("Starting embedded Kafka on port {}", kafkaPort);
        embeddedKafkaBroker = new EmbeddedKafkaBroker(kafkaPort, zookeeperPort);
        embeddedKafkaBroker.startup();

        return this;
    }

    public void stop() {
        try {
            LOGGER.info("Closing embedded Kafka on port {}", kafkaPort);
            embeddedKafkaBroker.close();
        } catch (Exception exception) {
            LOGGER.error("Failed closing embedded Kafka after runner closed", exception);
        }

        try {
            LOGGER.info("Closing embedded Zookeeper on port {}", zookeeperPort);
            embeddedZookeeper.close();
        } catch (Exception exception) {
            LOGGER.error("Failed closing embedded Zookeeper after runner closed", exception);
        }
    }
}
