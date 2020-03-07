package edu.eci.arsw;

import edu.eci.arsw.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@ComponentScan(basePackageClasses = UserRepository.class)
public class UniWheelsAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniWheelsAPIApplication.class, args);
    }
}