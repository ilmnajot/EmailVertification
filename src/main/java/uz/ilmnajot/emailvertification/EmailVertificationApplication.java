package uz.ilmnajot.emailvertification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class EmailVertificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailVertificationApplication.class, args);
    }

}
