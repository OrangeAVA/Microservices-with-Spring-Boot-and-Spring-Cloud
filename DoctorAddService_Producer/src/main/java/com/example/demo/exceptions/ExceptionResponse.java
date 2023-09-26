package com.example.demo.exceptions;

import java.util.Date;

public class ExceptionResponse {
	Date timeInfo;
	String message;
	String details;

	public ExceptionResponse(Date timeInfo, String message, String details) {
		super();
		this.timeInfo = timeInfo;
		this.message = message;
		this.details = details;
	}

	public Date getTimeInfo() {
		return timeInfo;
	}

	public void setTimeInfo(Date timeInfo) {
		this.timeInfo = timeInfo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "ExceptionResponse [timeInfo=" + timeInfo + ", message=" + message + ", details=" + details + "]";
	}

	public ExceptionResponse() {
		// TODO Auto-generated constructor stub
	}

}
