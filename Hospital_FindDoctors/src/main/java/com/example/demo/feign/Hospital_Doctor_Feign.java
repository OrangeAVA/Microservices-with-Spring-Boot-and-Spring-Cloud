package com.example.demo.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.pojos.Doctor;

@FeignClient(name="doctor-find-by-id-service",fallback = FeignClient_FallBack.class)//,url ="http://localhost:8085" )
public interface Hospital_Doctor_Feign {

	@GetMapping("/doctors/{doctorId}")
	ResponseEntity<Doctor> searchDoctorById(@PathVariable("doctorId")int doctorId);
	
}
