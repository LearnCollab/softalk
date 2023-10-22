package com.learncollab.softalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SoftalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoftalkApplication.class, args);
	}

}
