package com.flightbooking.flight.config;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightbooking.flight.util.JsonResponseEntity;
import com.flightbooking.flight.util.StringConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper mapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");

		JsonResponseEntity<?> body = new JsonResponseEntity<>();
		body.setStatus(StringConstants.failed);
		body.setMessage(StringConstants.InvalidJWT);
		body.setStatusCode(HttpStatus.UNAUTHORIZED);

		mapper.writeValue(response.getOutputStream(), body);
	}
}
