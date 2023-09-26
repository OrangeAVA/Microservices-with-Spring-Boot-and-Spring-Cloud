package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.DoctorDAO;
import com.example.demo.exceptions.DoctorAlreadyExistsException;
import com.example.demo.pojo.Doctor;

@RestController
public class DoctorAddController {
	
	@Autowired
	DoctorDAO doctorDAO;

	@PostMapping(path = "/doctors", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Doctor> createNewDoctorRecord(@RequestBody Doctor doctor)  throws DoctorAlreadyExistsException{
		// logic to add record in table will go here
		int added = 0;
			added = doctorDAO.addDoctor(doctor);
		if (added == 1)
			return new ResponseEntity<Doctor>(doctor,HttpStatusCode.valueOf(201));
	   
		return new ResponseEntity<Doctor>(HttpStatusCode.valueOf(204));
	}
	
	@PostMapping(path = "/doctors/form", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public Doctor createNewDoctorRecord_Form(Doctor doctor) {
		// logic to add record in table will go here
		Doctor d = null;
		int added = doctorDAO.addDoctor(doctor);
		if (added == 1)
			return doctor;

		return d;
	}
}
