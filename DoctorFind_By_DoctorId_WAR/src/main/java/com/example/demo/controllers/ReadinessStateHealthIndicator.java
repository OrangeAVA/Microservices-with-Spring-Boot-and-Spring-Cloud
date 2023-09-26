package com.example.demo.controllers;

import org.springframework.boot.actuate.availability.AvailabilityStateHealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.stereotype.Component;

@Component
public class ReadinessStateHealthIndicator extends AvailabilityStateHealthIndicator {

	public ReadinessStateHealthIndicator(ApplicationAvailability availability) {
		super(availability, ReadinessState.class, (statusMappings) -> {
			statusMappings.add(ReadinessState.ACCEPTING_TRAFFIC, Status.UP);
			statusMappings.add(ReadinessState.REFUSING_TRAFFIC, Status.DOWN);
		});
	}

	@Override
	protected AvailabilityState getState(ApplicationAvailability applicationAvailability) {
		return applicationAvailability.getReadinessState();
	}

}