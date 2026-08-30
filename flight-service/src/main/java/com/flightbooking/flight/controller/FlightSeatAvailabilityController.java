package com.flightbooking.flight.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.flight.dto.SeatAvailabilityRequestModel;
import com.flightbooking.flight.dto.SeatAvailabilityResponseModel;
import com.flightbooking.flight.service.FlightSeatAvailabilityService;
import com.flightbooking.flight.util.JsonResponseEntity;
import com.flightbooking.flight.util.StringConstants;
import com.flightbooking.flight.util.UrlConstants;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(UrlConstants.FLIGHT_SEAT_AVAILABILITY)
@RequiredArgsConstructor
public class FlightSeatAvailabilityController {

	private final FlightSeatAvailabilityService flightSeatAvailabilityService;

	@PostMapping(UrlConstants.RESERVE)
	public ResponseEntity<JsonResponseEntity<SeatAvailabilityResponseModel>> reserveSeats(
			@RequestBody final SeatAvailabilityRequestModel request) {

		JsonResponseEntity<SeatAvailabilityResponseModel> response = new JsonResponseEntity<>();

		SeatAvailabilityResponseModel result = flightSeatAvailabilityService.reserve(request);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordUpdateSuccessMessage);
		response.setResult(result);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(UrlConstants.RELEASE)
	public ResponseEntity<JsonResponseEntity<SeatAvailabilityResponseModel>> releaseSeats(
			@RequestBody final SeatAvailabilityRequestModel request) {

		JsonResponseEntity<SeatAvailabilityResponseModel> response = new JsonResponseEntity<>();

		SeatAvailabilityResponseModel result = flightSeatAvailabilityService.release(request);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordUpdateSuccessMessage);
		response.setResult(result);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
