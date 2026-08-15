package com.flightbooking.flight.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.flight.dto.FlightSeatConfigurationRequestModel;
import com.flightbooking.flight.entity.FlightSeatConfiguration;
import com.flightbooking.flight.service.FlightSeatConfigurationService;
import com.flightbooking.flight.util.JsonResponseEntity;
import com.flightbooking.flight.util.StringConstants;
import com.flightbooking.flight.util.UrlConstants;

@RestController
@RequestMapping(UrlConstants.FLIGHT_SEAT_CONFIGURATION)
public class FlightSeatConfigurationController {

	@Autowired
	private FlightSeatConfigurationService flightSeatConfigurationService;

	@PostMapping(UrlConstants.CREATE_FLIGHT_SEAT_CONFIGURATION)
	public ResponseEntity<JsonResponseEntity<Boolean>> createFlightSeatConfiguration(
			@RequestBody final FlightSeatConfigurationRequestModel model) {

		JsonResponseEntity<Boolean> response = new JsonResponseEntity<>();

		final Boolean result = flightSeatConfigurationService.createFlightSeatConfiguration(model);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordSavedSuccessMessage);
		response.setResult(result);
		response.setException(null);
		response.setStatusCode(HttpStatus.CREATED);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping(UrlConstants.GET_FLIGHT_SEAT_CONFIGURATION)
	public ResponseEntity<JsonResponseEntity<List<FlightSeatConfiguration>>> getFlightSeatConfiguration(
			@RequestParam final Long flightId) {

		JsonResponseEntity<List<FlightSeatConfiguration>> response = new JsonResponseEntity<>();

		final List<FlightSeatConfiguration> result = flightSeatConfigurationService
				.getFlightSeatConfigurationByFlightId(flightId);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordFetchSuccessMessage);
		response.setResult(result);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping(UrlConstants.UPDATE_FLIGHT_SEAT_CONFIGURATION)
	public ResponseEntity<JsonResponseEntity<Boolean>> updateFlightSeatConfiguration(@PathVariable final Long id,
			@RequestBody final FlightSeatConfigurationRequestModel model) {

		JsonResponseEntity<Boolean> response = new JsonResponseEntity<>();

		final Boolean result = flightSeatConfigurationService.updateFlightSeatConfiguration(id, model);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordUpdateSuccessMessage);
		response.setResult(result);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}