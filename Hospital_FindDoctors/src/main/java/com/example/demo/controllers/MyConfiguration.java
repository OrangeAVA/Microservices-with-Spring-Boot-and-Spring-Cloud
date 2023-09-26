package com.example.demo.controllers;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfiguration {

	/* added for interservice communication with load balancer and zipkin tracing*/
	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	/* added for interservice communication with load balancer*/
//	@LoadBalanced
//	@Bean
//	 RestTemplate restTemplate() {
//		return new RestTemplate();
//	}
}