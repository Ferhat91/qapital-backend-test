package qapital.broker.kafka.starter;

import org.apache.zookeeper.server.NIOServerCnxnFactory;
import org.apache.zookeeper.server.ServerCnxnFactory;
import org.apache.zookeeper.server.ZooKeeperServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;

public class EmbeddedZookeeper implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedZookeeper.class);

    private ServerCnxnFactory factory;

    private Integer port;

    private File snapshotDir;

    private File logDir;

    public EmbeddedZookeeper() {
        this.port = 2185;
    }

    public EmbeddedZookeeper(int port) {
        this.port = port;
    }

    public void startup() {
        LOGGER.info("Attempt to start ZooKeeper");

        try {
            snapshotDir = Files.createTempDirectory("zookeeper-snapshot").toFile();
            logDir = Files.createTempDirectory("zookeeper-logs").toFile();
        } catch (IOException e) {
            throw new RuntimeException("Unable to start Kafka", e);
        }
        try {
            int tickTime = 500;
            ZooKeeperServer zkServer = new ZooKeeperServer(snapshotDir, logDir, tickTime);
            this.factory = NIOServerCnxnFactory.createFactory();
            this.factory.configure(new InetSocketAddress("localhost", port), 256);
            factory.startup(zkServer);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            throw new RuntimeException("Unable to start ZooKeeper", e);
        }
    }

    @Override
    public void close() {
        if (factory != null) {
            factory.shutdown();
        }
    }
}
