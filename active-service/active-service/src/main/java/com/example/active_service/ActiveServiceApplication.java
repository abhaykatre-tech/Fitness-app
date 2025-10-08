package com.example.active_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ActiveServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActiveServiceApplication.class, args);
	}

}
