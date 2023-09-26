package com.example.demo.feign;

import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

public class CustomLoadBalancerConfiguration {

	@Bean
	public ServiceInstanceListSupplier sameClientServiceInstanceListSupplier(ConfigurableApplicationContext context) {
		return ServiceInstanceListSupplier.builder().withBlockingDiscoveryClient().withSameInstancePreference().build(context);
	}
}
