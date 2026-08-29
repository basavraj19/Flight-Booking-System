package com.flightbooking.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.booking.dto.BookingRequestModel;
import com.flightbooking.booking.dto.BookingResponseModel;
import com.flightbooking.booking.service.BookingService;
import com.flightbooking.booking.util.JsonResponseEntity;
import com.flightbooking.booking.util.StringConstants;
import com.flightbooking.booking.util.UrlConstants;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(UrlConstants.BOOKING)
@RequiredArgsConstructor
public class BookingController {

	private final BookingService bookingService;

	@PostMapping(UrlConstants.CREATE_BOOKING)
	public ResponseEntity<JsonResponseEntity<BookingResponseModel>> createNewBooking(
			@RequestBody final BookingRequestModel request) {

		JsonResponseEntity<BookingResponseModel> response = new JsonResponseEntity<>();

		final BookingResponseModel booking = bookingService.createNewBooking(request);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.bookingCreatedSuccessMessage);
		response.setResult(booking);
		response.setException(null);
		response.setStatusCode(HttpStatus.CREATED);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
