package com.flightbooking.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.admin.dto.FlightRequestModel;
import com.flightbooking.admin.dto.FlightResponseModel;
import com.flightbooking.admin.service.FlightService;
import com.flightbooking.admin.util.JsonResponseEntity;
import com.flightbooking.admin.util.StringConstants;
import com.flightbooking.admin.util.UrlConstants;

@RestController
@RequestMapping(UrlConstants.FLIGHT)
public class FlightController {

	@Autowired
	private FlightService flightService;

	@PostMapping(UrlConstants.CREATE_NEW_FLIGHT_ENTRY)
	public ResponseEntity<JsonResponseEntity<FlightResponseModel>> newFlight(
			@RequestBody final FlightRequestModel request) {

		JsonResponseEntity<FlightResponseModel> response = new JsonResponseEntity<>();

		final FlightResponseModel result = flightService.createNewFlightEntry(request);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordSavedSuccessMessage);
		response.setResult(result);
		response.setException(null);
		response.setStatusCode(HttpStatus.CREATED);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping(UrlConstants.FETCH_FLIGHT_BY_NUMBER)
	public ResponseEntity<JsonResponseEntity<FlightResponseModel>> getFlightyNumber(
			@PathVariable(value = StringConstants.FLIGHT_NUMBER) final String flightNumber) {
		JsonResponseEntity<FlightResponseModel> response = new JsonResponseEntity<>();

		final FlightResponseModel flightDtl = flightService.getDetailsByFlightNumber(flightNumber);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordFetchSuccessMessage);
		response.setResult(flightDtl);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(UrlConstants.FTECH_FLIGHTS_BY_AIRLINE)
	public ResponseEntity<JsonResponseEntity<List<FlightResponseModel>>> getFlightsByAirline(
			@PathVariable(value = StringConstants.AIRLINE_ID) final Long airlineId) {

		JsonResponseEntity<List<FlightResponseModel>> response = new JsonResponseEntity<>();

		final List<FlightResponseModel> dataList = flightService.getAllFlightsByAirline(airlineId);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordFetchSuccessMessage);
		response.setResult(dataList);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}
