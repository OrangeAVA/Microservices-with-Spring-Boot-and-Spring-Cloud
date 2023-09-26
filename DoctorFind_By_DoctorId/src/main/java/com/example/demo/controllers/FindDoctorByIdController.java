package com.example.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.Doctor;
import com.example.demo.repository.DoctorRepository;

import io.micrometer.core.annotation.Timed;

@RestController
public class FindDoctorByIdController {

	@Autowired
	DoctorRepository repo;

//	@GetMapping("/doctors/{doctorId}")
//	ResponseEntity<Doctor> findDoctorById(@PathVariable int doctorId) {
//		Optional<Doctor> optional = repo.findById(doctorId);
//		if (optional.isPresent()) {
//			Doctor doctor = optional.get();
//			return new ResponseEntity<Doctor>(doctor, HttpStatus.OK);
//		}
//
//		return new ResponseEntity<Doctor>(HttpStatus.NO_CONTENT);
//	}

	@Timed(value = "execution.time", description = "Time taken to return Doctor")
	@GetMapping("/doctors/{doctorId}")
	ResponseEntity<Doctor> findDoctorById(@PathVariable int doctorId, @RequestHeader(name = "sort",defaultValue = "all") String sort) {
		System.out.println(sort);
		
		Optional<Doctor> optional = repo.findById(doctorId);
		if (optional.isPresent()) {
			
			Doctor doctor = optional.get();
			
			if (sort.equals("all") || doctor.getSpecialization().equals(sort))
				return new ResponseEntity<Doctor>(doctor, HttpStatus.OK);
		}
	
		return new ResponseEntity<Doctor>(HttpStatus.NO_CONTENT);
	}
	
	//added for routing purpose 
	@GetMapping("/doctors-values/{doctorId}")
	ResponseEntity<Doctor> findDoctorById(@PathVariable int doctorId, @RequestParam String value, @RequestHeader(name = "sort",defaultValue = "all") String sort) {
		System.out.println(sort);
		System.out.println(value);
		Optional<Doctor> optional = repo.findById(doctorId);
		if (optional.isPresent()) {
			Doctor doctor = optional.get();
			if (sort.equals("all") || doctor.getSpecialization().equals(sort))
				return new ResponseEntity<Doctor>(doctor, HttpStatus.OK);
		}

		return new ResponseEntity<Doctor>(HttpStatus.NO_CONTENT);
	}

}
