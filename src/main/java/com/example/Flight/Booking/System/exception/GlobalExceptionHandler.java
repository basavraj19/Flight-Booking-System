package com.example.Flight.Booking.System.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.Flight.Booking.System.util.JsonResponseEntity;
import com.example.Flight.Booking.System.util.StringConstants;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidInputException.class)
	public JsonResponseEntity<?> handleInvalidInputException(final InvalidInputException exception) {
		JsonResponseEntity<?> response = new JsonResponseEntity<>();
		response.setStatus(StringConstants.failed);
		response.setResult(null);
		response.setMessage(exception.getMessage());
		response.setStatusCode(HttpStatus.BAD_REQUEST);
		response.setException(null);
		return response;
	}

	@ExceptionHandler(DuplicateResourceException.class)
	public JsonResponseEntity<?> handleDuplicateResourceException(final DuplicateResourceException exception) {
		JsonResponseEntity<?> response = new JsonResponseEntity<>();
		response.setStatus(StringConstants.failed);
		response.setResult(null);
		response.setMessage(exception.getMessage());
		response.setStatusCode(HttpStatus.BAD_REQUEST);
		response.setException(null);
		return response;
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public JsonResponseEntity<?> handleResourceNotFoundException(final ResourceNotFoundException exception) {
		JsonResponseEntity<?> response = new JsonResponseEntity<>();
		response.setStatus(StringConstants.failed);
		response.setResult(null);
		response.setMessage(exception.getMessage());
		response.setStatusCode(HttpStatus.NOT_FOUND);
		response.setException(null);
		return response;
	}
}
