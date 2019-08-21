package com.so4it.test.kafka;



import org.apache.zookeeper.server.NIOServerCnxnFactory;
import org.apache.zookeeper.server.ServerCnxnFactory;
import org.apache.zookeeper.server.ZooKeeperServer;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbeddedZookeeper implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedZookeeper.class);
    
    private final int port;

    private ServerCnxnFactory factory;
    
    private File snapshotDir;
    private File logDir;

    public EmbeddedZookeeper() {
        this.port = 2185;
    }

    public EmbeddedZookeeper(int port) {
        this.port = port;
    }

    public void startup() {

        try {
            snapshotDir = Files.createTempDirectory("zookeeper-snapshot").toFile();
            logDir = Files.createTempDirectory("zookeeper-logs").toFile();
        } catch (IOException e) {
            throw new RuntimeException("Unable to start Kafka", e);
        }

        addTempDirectoryDeletionShutdownHook(snapshotDir);
        addTempDirectoryDeletionShutdownHook(logDir);

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
    public void close() throws Exception {
        if (factory != null) {
            factory.shutdown();
        }
        
        deleteTempDirectory(logDir);
        deleteTempDirectory(snapshotDir);
    }

    private void addTempDirectoryDeletionShutdownHook(File logDirectory) {
//        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Files.walkFileTree(logDirectory.toPath(), new SimpleFileVisitor<Path>() {
//                        @Override
//                        public FileVisitResult visitFile(Path file,
//                                @SuppressWarnings("unused") BasicFileAttributes attrs)
//                                throws IOException {
//                            Files.delete(file);
//                            return FileVisitResult.CONTINUE;
//                        }
//
//                        @Override
//                        public FileVisitResult postVisitDirectory(Path dir, IOException exception)
//                                throws IOException {
//                            if (exception == null) {
//                                Files.delete(dir);
//                                return FileVisitResult.CONTINUE;
//                            }
//                            // directory iteration failed
//                            throw exception;
//                        }
//                    });
//                } catch (Exception exception) {
//                    throw new RuntimeException(String.format("Failed to delete Zookeeper log directory. directory=%s",logDirectory), exception);
//                }
//            }
//        }));
    }

    private void deleteTempDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }
        
        Files.walkFileTree(directory.toPath(), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file,
                    @SuppressWarnings("unused") BasicFileAttributes attrs)
                    throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exception)
                    throws IOException {
                if (exception == null) {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
                // directory iteration failed
                throw exception;
            }
        });
    }
}
