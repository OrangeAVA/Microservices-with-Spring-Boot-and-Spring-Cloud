package com.example.demo.feign;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.demo.pojos.Doctor;

@Component
public class FeignClient_FallBack implements Hospital_Doctor_Feign{

	@Override
	public ResponseEntity<Doctor> searchDoctorById(int doctorId) {
		// TODO Auto-generated method stub
		return new ResponseEntity<Doctor>(new Doctor(1, "ABCD", "No spec"),HttpStatus.OK);
	}

}
