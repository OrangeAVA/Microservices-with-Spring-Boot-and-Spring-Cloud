package com.example.demo.exceptions;



public class DoctorAlreadyExistsException extends RuntimeException {

	private String message;

	public DoctorAlreadyExistsException() {
		// TODO Auto-generated constructor stub
		this("We have similar record");
	}

	public DoctorAlreadyExistsException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
