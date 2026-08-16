package com.flightbooking.apigateway.utils;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsonResponseEntity<T> {

	private String status;

	private String message;

	private T result;

	private Exception exception;

	private HttpStatus statusCode;
}