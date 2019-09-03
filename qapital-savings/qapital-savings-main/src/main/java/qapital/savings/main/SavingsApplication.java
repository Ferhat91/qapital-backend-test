package qapital.savings.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:qapital-savings-rest-service.xml"})
public class SavingsApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SavingsApplication.class, args);
    }
}
