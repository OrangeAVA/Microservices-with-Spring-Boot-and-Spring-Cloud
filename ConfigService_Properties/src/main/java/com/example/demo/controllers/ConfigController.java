package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.MyConfig;
import com.example.demo.pojo.CompanyPojo;

@RestController
public class ConfigController {

	@Autowired
	MyConfig config;
	
	@GetMapping("/values")
	public CompanyPojo getValues_actual() {
		return new CompanyPojo(config.getCompName(),config.getPincode());
	}
}






