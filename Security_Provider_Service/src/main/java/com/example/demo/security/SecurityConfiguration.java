package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.server.SecurityWebFilterChain;



@Configuration
public class SecurityConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityWebFilterChain configureResourceServer(ServerHttpSecurity httpSecurity) throws Exception {
		// for doctors only
//	  return httpSecurity.authorizeExchange().pathMatchers(HttpMethod.GET, "/doctors1/**").
//			  authenticated().and()
//		.oauth2ResourceServer().jwt()
//	    .and().and().build();

		//permitting /actuator but not /doctors/**
//	  return httpSecurity.authorizeExchange().pathMatchers(HttpMethod.GET, "/doctors/**").
//			  authenticated().and().
//			  authorizeExchange().pathMatchers(HttpMethod.GET, "/actuator/**").permitAll().and()
//		.oauth2ResourceServer().jwt()
//	    .and().and().build();
       
		//securing all endpoints
		return httpSecurity.authorizeExchange().pathMatchers(HttpMethod.GET, "/doctors/**").authenticated().and()
				.authorizeExchange()
				.pathMatchers(HttpMethod.GET, "/hospitals/**", "/hospitals-balanced/**",
						"/hospitals-balanced-gateway/**", "/hospitals-feign/**","/hospitals-feign-interceptor/**","/hospitals-feign-relay/**")
				.authenticated().and().authorizeExchange().pathMatchers("/actuator/health/**")
				.permitAll().anyExchange().authenticated().and().oauth2ResourceServer().jwt().and().and().build();


	}

}
