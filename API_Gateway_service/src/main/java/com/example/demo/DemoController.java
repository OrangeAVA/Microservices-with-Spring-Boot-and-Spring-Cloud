package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	@GetMapping("/dummy")
	public ResponseEntity getDummyResponse() {
		return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
	}
}
