package com.example.demo;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.example.demo.feign.CustomLoadBalancerConfiguration;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.demo.feign")
@EnableDiscoveryClient
@LoadBalancerClient(name = "doctor-find-by-id-service",configuration=CustomLoadBalancerConfiguration.class,value ="doctor-find-by-id-service" )
public class HospitalFindDoctorsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalFindDoctorsApplication.class, args);
	}


}
