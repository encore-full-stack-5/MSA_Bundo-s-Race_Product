package com.example.bundosRace;

import com.example.bundosRace.repository.elastic.ElasticProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.SpringVersion;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableFeignClients
@EnableJpaAuditing
@SpringBootApplication
public class BundosRaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BundosRaceApplication.class, args);
	}
}
