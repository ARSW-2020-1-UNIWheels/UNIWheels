package edu.eci.arsw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"edu.eci.arsw"})
@ComponentScan(basePackageClasses = edu.eci.arsw.uniwheels.repository.UserRepository.class)
@EnableJpaRepositories
public class UniwheelsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniwheelsApplication.class, args);
	}

}
