package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.demo.pojos.Doctor;

@FeignClient(name="api-gateway-service",fallback = FeignClient_FallBack.class)//,url ="http://localhost:8085" )
public interface Hospital_Doctor_Feign {

	@GetMapping("/doctors/{doctorId}")
	ResponseEntity<Doctor> searchDoctorById(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,@PathVariable("doctorId")int doctorId);
	
	@GetMapping("/doctors/{doctorId}")
	ResponseEntity<Doctor> searchDoctorById(@PathVariable("doctorId")int doctorId);

	
}
