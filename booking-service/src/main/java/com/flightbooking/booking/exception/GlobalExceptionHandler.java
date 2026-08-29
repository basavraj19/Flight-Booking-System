package com.flightbooking.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flightbooking.booking.util.JsonResponseEntity;
import com.flightbooking.booking.util.StringConstants;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<JsonResponseEntity<?>> handleInvalidInputException(final InvalidInputException exception) {

		JsonResponseEntity<?> response = new JsonResponseEntity<>();

		response.setStatus(StringConstants.failed);
		response.setResult(null);
		response.setMessage(exception.getMessage());
		response.setStatusCode(HttpStatus.BAD_REQUEST);
		response.setException(null);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<JsonResponseEntity<?>> handleResourceNotFoundException(
			final ResourceNotFoundException exception) {
		JsonResponseEntity<?> response = new JsonResponseEntity<>();

		response.setStatus(StringConstants.failed);
		response.setResult(null);
		response.setMessage(exception.getMessage());
		response.setStatusCode(HttpStatus.NOT_FOUND);
		response.setException(null);

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
}
