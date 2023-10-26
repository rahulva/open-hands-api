package com.project.open_hands;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestDemoApplication {

	@Bean
	@ServiceConnection
	MySQLContainer<?> mysqlContainer() {
		return new MySQLContainer<>("mysql:latest")
				.withDatabaseName("open_hands_test");
	}

	public static void main(String[] args) {
		SpringApplication.from(OpenHandsApiApplication::main).with(TestDemoApplication.class).run(args);
	}

}
