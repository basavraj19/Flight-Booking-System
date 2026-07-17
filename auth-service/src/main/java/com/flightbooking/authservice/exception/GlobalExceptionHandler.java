package com.flightbooking.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flightbooking.authservice.util.JsonResponseEntity;
import com.flightbooking.authservice.util.StringConstants;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<JsonResponseEntity<?>> handleInvalidInputException(final InvalidInputException exception) {
		JsonResponseEntity<?> response = new JsonResponseEntity<>();
		response.setStatus(StringConstants.failed);
		response.setMessage(exception.getMessage());
		response.setResult(null);
		response.setException(null);
		response.setStatusCode(HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<JsonResponseEntity<?>> handleDuplicateResourceException(
			final DuplicateResourceException exception) {

		JsonResponseEntity<?> response = new JsonResponseEntity<>();

		response.setStatus(StringConstants.failed);
		response.setResult(null);
		response.setMessage(exception.getMessage());
		response.setStatusCode(HttpStatus.CONFLICT);
		response.setException(null);

		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<JsonResponseEntity<?>> handleUserNotFoundException(final UserNotFoundException exception) {

		JsonResponseEntity<?> response = new JsonResponseEntity<>();

		response.setStatus(StringConstants.failed);
		response.setResult(null);
		response.setMessage(exception.getMessage());
		response.setStatusCode(HttpStatus.NOT_FOUND);
		response.setException(null);

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
}
