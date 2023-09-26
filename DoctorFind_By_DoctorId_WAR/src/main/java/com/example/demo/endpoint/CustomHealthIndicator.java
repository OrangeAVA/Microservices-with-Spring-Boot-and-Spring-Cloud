package com.example.demo.endpoint;

import java.net.Socket;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

	private static final String URL = "http://localhost";

	@Override
	public Health health() {
		try (Socket socket = new Socket(new java.net.URL(URL).getHost(), 8761)) {
		} catch (Exception e) {
			System.out.println("Failed to connect to: " + URL);
			return Health.down().withDetail("Eureka connection is down", e.getMessage()).build();
		}
		return Health.up().build();
	}
}
