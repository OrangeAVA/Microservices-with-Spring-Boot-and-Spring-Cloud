package com.example.demo.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	@GetMapping("/hello")
	public String getInfo() {
		return "hello";
	}
	
	@GetMapping("/welcome")
	public String getWelcome() {
		return "welcom";
	}
}
