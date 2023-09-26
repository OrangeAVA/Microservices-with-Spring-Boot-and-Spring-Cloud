package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.DoctorDAO;
import com.example.demo.exceptions.DoctorAlreadyExistsException;
import com.example.demo.pojo.Doctor;

@RestController
public class DoctorAddController {
	
	@Autowired
	StreamBridge bridge;

	
	@Autowired
	DoctorDAO doctorDAO;

	@PostMapping(path = "/doctors", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Doctor> createNewDoctorRecord(@RequestBody Doctor doctor)  throws DoctorAlreadyExistsException{
		// logic to add record in table will go here
		int added = 0;
			added = doctorDAO.addDoctor(doctor);
		if (added == 1)
		{
			boolean isPublished = bridge.send("doctorSupplier-out-0", MessageBuilder.withPayload(doctor).build());
			System.out.println("data published :"+ isPublished);
			return new ResponseEntity<Doctor>(doctor,HttpStatusCode.valueOf(201));
		}
	   
		return new ResponseEntity<Doctor>(HttpStatusCode.valueOf(204));
	}
	

}
