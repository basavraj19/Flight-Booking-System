package com.flightbooking.authservice.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.authservice.dto.LoginRequestDto;
import com.flightbooking.authservice.dto.UpdatePasswordRequest;
import com.flightbooking.authservice.dto.UpdateUserRequestDto;
import com.flightbooking.authservice.dto.UserAccountDto;
import com.flightbooking.authservice.service.UserService;
import com.flightbooking.authservice.util.JsonResponseEntity;
import com.flightbooking.authservice.util.StringConstants;
import com.flightbooking.authservice.util.UrlConstants;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(UrlConstants.user)
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping(UrlConstants.createNewUser)
	public ResponseEntity<JsonResponseEntity<UserAccountDto>> createNewUser(@Valid @RequestBody UserAccountDto user) {
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
	public ResponseEntity<JsonResponseEntity<?>> loginUser(@Valid @RequestBody LoginRequestDto request) {

		String jwtToken = userService.loginUser(request);

		final long expiryTime = 5 * 60;

		final ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", jwtToken).httpOnly(true).secure(false)
				.path("/").sameSite("Lax").maxAge(expiryTime).build();

		JsonResponseEntity<UserAccountDto> response = new JsonResponseEntity<>();
		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.userLoggedInMessage);
		response.setException(null);
		response.setStatusCode(HttpStatus.ACCEPTED);

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response);
	}

	@GetMapping(UrlConstants.getUserDetails)
	public ResponseEntity<JsonResponseEntity<UserAccountDto>> getUserDetails(
			@RequestParam(StringConstants.username) final String username) {

		final UserAccountDto newUser = userService.getUserByUsername(username);

		JsonResponseEntity<UserAccountDto> response = new JsonResponseEntity<>();
		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.fetchUserDetailsSuccessMessage);
		response.setResult(newUser);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping(UrlConstants.updatePassword)
	public ResponseEntity<JsonResponseEntity<?>> updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {

		userService.updateUserPassword(request);

		JsonResponseEntity<?> response = new JsonResponseEntity<>();
		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.passwordUpdateMessage);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping(UrlConstants.deleteUser)
	public ResponseEntity<JsonResponseEntity<?>> deleteUser(@Valid @RequestBody LoginRequestDto request) {

		userService.deleteUserAccount(request);

		JsonResponseEntity<?> response = new JsonResponseEntity<>();
		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.userDeletedMessage);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping(UrlConstants.updateUser)
	public ResponseEntity<JsonResponseEntity<UpdateUserRequestDto>> updateUserDetails(
			@Valid @RequestBody UpdateUserRequestDto user) {

		UpdateUserRequestDto updatedUser = userService.updateUserDetails(user);

		JsonResponseEntity<UpdateUserRequestDto> response = new JsonResponseEntity<>();
		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.userUpdateMessage);
		response.setResult(updatedUser);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(UrlConstants.FetchUser)
	public ResponseEntity<JsonResponseEntity<Long>> getUserIdByUsername(@PathVariable final String username) {

		JsonResponseEntity<Long> response = new JsonResponseEntity<>();

		Long userId = userService.getUserIdByUsername(username);

		response.setResult(userId);
		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.fetchUserDetailsSuccessMessage);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
