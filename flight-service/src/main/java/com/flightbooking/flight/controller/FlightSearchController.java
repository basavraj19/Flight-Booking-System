package com.flightbooking.flight.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.flight.dto.SearchFlightRequestModel;
import com.flightbooking.flight.dto.SearchFlightResponseModel;
import com.flightbooking.flight.service.FlightSearchService;
import com.flightbooking.flight.util.JsonResponseEntity;
import com.flightbooking.flight.util.StringConstants;
import com.flightbooking.flight.util.UrlConstants;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(UrlConstants.SEARCH_FLIGHTS)
@RequiredArgsConstructor
public class FlightSearchController {

	private final FlightSearchService flightSearchService;

	@PostMapping("/")
	public ResponseEntity<JsonResponseEntity<List<SearchFlightResponseModel>>> getAllFlights(
			@RequestBody final SearchFlightRequestModel request) {

		JsonResponseEntity<List<SearchFlightResponseModel>> response = new JsonResponseEntity<>();

		List<SearchFlightResponseModel> flightDetails = flightSearchService.searchFlights(request);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordFetchSuccessMessage);
		response.setResult(flightDetails);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
