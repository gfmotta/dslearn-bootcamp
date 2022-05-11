package com.devsuperior.dslearnbds.controllers.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dslearnbds.services.exceptions.DatabaseException;
import com.devsuperior.dslearnbds.services.exceptions.ForbiddenException;
import com.devsuperior.dslearnbds.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dslearnbds.services.exceptions.UnauthorizedException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		StandardError error = new StandardError();
		error.setTimespan(Instant.now());
		error.setStatus(status.value());
		error.setError("Resource not found");
		error.setMessage(exception.getMessage());
		error.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException exception, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		StandardError error = new StandardError();
		error.setTimespan(Instant.now());
		error.setStatus(status.value());
		error.setError("Database exception");
		error.setMessage(exception.getMessage());
		error.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> argumentNotValid(MethodArgumentNotValidException exception, HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		
		ValidationError error = new ValidationError();
		error.setTimespan(Instant.now());
		error.setStatus(status.value());
		error.setError("Validation exception");
		error.setMessage(exception.getMessage());
		error.setPath(request.getRequestURI());
		
		for (FieldError x : exception.getBindingResult().getFieldErrors()) {
			error.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<OAuthCustomError> forbidden(ForbiddenException exception, HttpServletRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		
		OAuthCustomError error = new OAuthCustomError();
		error.setError("Authorization error");
		error.setErrorDescription(exception.getMessage());
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<OAuthCustomError> unauthorized(UnauthorizedException exception, HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		
		OAuthCustomError error = new OAuthCustomError();
		error.setError("Authorization error");
		error.setErrorDescription(exception.getMessage());

		return ResponseEntity.status(status).body(error);
	}
}
