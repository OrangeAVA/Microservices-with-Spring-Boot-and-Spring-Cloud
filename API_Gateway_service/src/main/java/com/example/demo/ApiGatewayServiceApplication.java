package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.cache.LocalResponseCacheGatewayFilterFactory;
import org.springframework.context.annotation.Bean;

//import com.example.demo.filters.MyLoggingFilter;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayServiceApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(ApiGatewayServiceApplication.class, args);
	}
	
	
//	@Bean
//	public GlobalFilter requestFilter(){
//	   return new MyLoggingFilter();
//	}
}
