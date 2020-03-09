package edu.eci.arsw.uniwheelsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"edu.eci.arsw.uniwheels"})
public class UniwheelsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniwheelsApplication.class, args);
	}

}
