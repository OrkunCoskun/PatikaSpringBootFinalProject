package com.orkuncoskun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class Patikafinalproject01Application {

	public static void main(String[] args) {
		SpringApplication.run(Patikafinalproject01Application.class, args);
	}

}
