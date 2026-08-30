package com.flightbooking.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flightbooking.booking.util.JsonResponseEntity;
import com.flightbooking.booking.util.StringConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private final ObjectMapper objectMapper;

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

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<JsonResponseEntity<?>> handleFeignException(final FeignException exception) {

		HttpStatus status = HttpStatus.valueOf(exception.status());

		JsonResponseEntity<?> response = new JsonResponseEntity<>();

		try {
			response = objectMapper.readValue(exception.contentUTF8(), JsonResponseEntity.class);
		} catch (Exception e) {
			response.setStatus(StringConstants.failed);
			response.setMessage("Unable to process the request.");
			response.setResult(null);
			response.setException(null);
			response.setStatusCode(status);
		}

		return new ResponseEntity<>(response, status);
	}

	@ExceptionHandler(RetryableException.class)
	public ResponseEntity<JsonResponseEntity<?>> handleFeignRetryableException(final RetryableException exception) {

		JsonResponseEntity<?> response = new JsonResponseEntity<>();

		response.setStatus(StringConstants.failed);
		response.setMessage("The requested service is currently unavailable. Please try again later.");
		response.setResult(null);
		response.setException(null);
		response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);

		return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
	}
}
