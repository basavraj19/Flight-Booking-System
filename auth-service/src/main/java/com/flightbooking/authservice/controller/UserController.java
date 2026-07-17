package com.flightbooking.authservice.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.authservice.dto.LoginRequestDto;
import com.flightbooking.authservice.dto.UserAccountDto;
import com.flightbooking.authservice.service.UserService;
import com.flightbooking.authservice.util.JsonResponseEntity;
import com.flightbooking.authservice.util.StringConstants;
import com.flightbooking.authservice.util.UrlConstants;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(UrlConstants.user)
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping(UrlConstants.createNewUser)
	public ResponseEntity<JsonResponseEntity<?>> createNewUser(@RequestBody UserAccountDto user) {
		final UserAccountDto newUser = userService.createNewUser(user);

		JsonResponseEntity<UserAccountDto> response = new JsonResponseEntity<>();
		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.userCreatedMessage);
		response.setResult(newUser);
		response.setException(null);
		response.setStatusCode(HttpStatus.CREATED);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping(UrlConstants.loginUser)
	public ResponseEntity<JsonResponseEntity<?>> loginUser(@RequestBody LoginRequestDto request) {

		String jwtToken = userService.loginUser(request);

		final long expiryTime = 5 * 60;

		final ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", jwtToken).httpOnly(true).secure(false)
				.path("/").sameSite("Lax").maxAge(expiryTime).build();

		JsonResponseEntity<UserAccountDto> response = new JsonResponseEntity<>();
		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.userLoggedInMessage);
		response.setResult(null);
		response.setException(null);
		response.setStatusCode(HttpStatus.ACCEPTED);

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response);
	}
}
