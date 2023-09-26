package com.example.demo.config;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import com.example.demo.pojo.Doctor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayRouting {

//	  @Bean
//	    public RouteLocator configureRoute(RouteLocatorBuilder builder) {
//	       return builder.routes()
//	      .route("hospital_route", r->r.path("/hospitals/**").uri("http://localhost:9091/hospitals")) 
//	      .route("doctor_route", r->r.path("/doctors/**").uri("lb://doctor-find-by-id-service/doctors")) 
//	      .build();
//	    }

//	  The RewritePath GatewayFilter factory 

//	@Bean
//	public RouteLocator configureRoute(RouteLocatorBuilder builder) {
//		return builder.routes()
//				
//				.route("doctorById",r -> r.path("/mydoctors/**")
//						.filters(rw -> rw.rewritePath("/mydoctors/(?<segment>/?.*)", "/doctors/$\\{segment}"))
//								.uri("lb://doctor-find-by-id-service"))
//				.route("hospitalById", r -> r.path("/myhospitals/**")
//						.filters(rw -> rw.rewritePath("/myhospitals/(?<segment>/?.*)", "/hospitals/$\\{segment}"))
//						.uri("http://localhost:9091")) 
//				.build();
//	}

//	  @Bean
//	    public RouteLocator configureRoute(RouteLocatorBuilder builder) {
//	       return builder.routes()
//	      .route("hospital", r->r.path("/hospitals-balanced-gateway/**").filters(f -> f.addRequestHeader("sort", "Medicine")).
//	    		  uri("http://localhost:9091/hospitals-balanced-gateway")) //static routing
//	      
//	      .route("doctor", r->r.path("/doctors/**")
//	    		  .filters(f -> f.addRequestHeader("sort", "Medicine"))
//	    		  .uri("lb://doctor-find-by-id-service")) 
//	      
//	      .build();
//	    }

////	 The MapRequestHeader GatewayFilter 
//	
//	 @Bean
//	    public RouteLocator configureRoute(RouteLocatorBuilder builder) {
//	       return builder.routes()
//	      .route("hospital", r->r.path("/hospitals/**").uri("http://localhost:9091/hospitals")) //static routing
//	      .route("doctor_map_request_header -", r->r.path("/doctors/**")
//	    		  .filters(f -> f.mapRequestHeader("spec", "sort")).
//	    		  uri("lb://doctor-find-by-id-service")) 
//	      .build();
//	    }

////	 The Modify request body GatewayFilter 
//	
//	 @Bean
//	    public RouteLocator configureRoute(RouteLocatorBuilder builder) {
//	       return builder.routes()
//	      .route("doctor_modify_request_body -", r->r.path("/doctors/**")
//	    		  .filters(f -> f.modifyRequestBody(Doctor.class, Doctor.class, (exchange, s) -> Mono.just(new Doctor(s.getDoctorId(),
//	    				  s.getDoctorName().toUpperCase(),s.getSpecialization())))).
//	    		  uri("http://localhost:8081")) 
//	      .build();
//	    }


}
