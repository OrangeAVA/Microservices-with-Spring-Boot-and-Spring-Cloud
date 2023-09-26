package com.example.demo.controllers;

import java.sql.SQLException;


import java.util.ArrayList;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.Doctor;
import com.example.demo.repository.DoctorRepository;

@RestController
public class DoctorFindAllController {

	@Autowired
	DoctorRepository repo;
	
	@Autowired
	DataSource dataSource;
	
	@GetMapping(value = "/doctors")
	public ResponseEntity<List<Doctor>> findAllDoctors() {
		// logic to find all doctors will go here
		List<Doctor> doctors = new ArrayList<>();
		doctors = repo.findAll();
		try {
			System.out.println(dataSource.getConnection().getMetaData().getURL());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(doctors.size()>0) {
			return new ResponseEntity<List<Doctor>>(doctors, HttpStatusCode.valueOf(200));
		}
		return new ResponseEntity<List<Doctor>>(HttpStatus.NO_CONTENT);
		
	}
	
}
