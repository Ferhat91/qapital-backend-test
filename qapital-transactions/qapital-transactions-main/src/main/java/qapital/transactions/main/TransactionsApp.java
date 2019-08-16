package qapital.transactions.main;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:transactions-rest-service.xml"})
public class TransactionsApp {

    public static void main(String[] args) {
        SpringApplication.run(TransactionsApp.class, args);
    }
}
