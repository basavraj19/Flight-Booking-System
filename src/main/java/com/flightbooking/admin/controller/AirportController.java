package com.flightbooking.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.admin.dto.AirportModel;
import com.flightbooking.admin.entity.Airport;
import com.flightbooking.admin.util.JsonResponseEntity;
import com.flightbooking.admin.util.StringConstants;
import com.flightbooking.admin.util.UrlConstants;

@RestController
@RequestMapping(UrlConstants.AIRPORT)
public class AirportController {

	
	@PostMapping(UrlConstants.CREATE_NEW_AIRPORT)
	public ResponseEntity<JsonResponseEntity<Airport>> createNewAirlineEntry(@RequestBody final AirportModel model) {

		JsonResponseEntity<Airport> response = new JsonResponseEntity<>();

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordSavedSuccessMessage);
		response.setResult(null);
		response.setException(null);
		response.setStatusCode(HttpStatus.CREATED);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
