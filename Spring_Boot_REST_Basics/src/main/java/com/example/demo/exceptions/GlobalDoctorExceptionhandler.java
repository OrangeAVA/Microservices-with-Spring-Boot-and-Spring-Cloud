package com.example.demo.exceptions;

import java.util.Date;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
public class GlobalDoctorExceptionhandler {

	@ExceptionHandler(DoctorAlreadyExistsException.class)
//	@ResponseStatus(code = HttpStatus.NOT_MODIFIED)
	protected ResponseEntity<Object> handleMyException(Exception ex, WebRequest rq) {

		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
				ex.getMessage(),
				rq.getDescription(false));
		return new ResponseEntity<Object>(exceptionResponse,HttpStatusCode.valueOf(204));
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleAllException(Exception ex, WebRequest rq) {

		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
				ex.getMessage(),
				rq.getDescription(false));
		return new ResponseEntity<Object>(exceptionResponse,HttpStatus.CONFLICT);
	}

}
