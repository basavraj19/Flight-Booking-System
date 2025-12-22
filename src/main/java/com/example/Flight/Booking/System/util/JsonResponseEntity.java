package com.example.Flight.Booking.System.util;

import org.springframework.http.HttpStatusCode;

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

	private HttpStatusCode statusCode;
}
