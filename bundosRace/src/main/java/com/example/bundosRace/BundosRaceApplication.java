package com.example.bundosRace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;


@EnableFeignClients
@EnableJpaAuditing
@SpringBootApplication
public class BundosRaceApplication {

	@Bean
	public RecordMessageConverter converter() {
		return new JsonMessageConverter();
	}

	public static void main(String[] args) {
		SpringApplication.run(BundosRaceApplication.class, args);
	}
}
