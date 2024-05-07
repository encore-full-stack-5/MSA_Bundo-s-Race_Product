package com.example.bundosRace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BundosRaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BundosRaceApplication.class, args);
	}

}
