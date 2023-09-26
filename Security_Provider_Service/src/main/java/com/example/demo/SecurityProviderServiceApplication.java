package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com")
@EnableDiscoveryClient
public class SecurityProviderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityProviderServiceApplication.class, args);
	}

}
