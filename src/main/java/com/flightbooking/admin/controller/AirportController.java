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

import com.flightbooking.admin.dto.AirportModel;
import com.flightbooking.admin.service.AirportService;
import com.flightbooking.admin.util.JsonResponseEntity;
import com.flightbooking.admin.util.StringConstants;
import com.flightbooking.admin.util.UrlConstants;

@RestController
@RequestMapping(UrlConstants.AIRPORT)
public class AirportController {

	@Autowired
	private AirportService airportService;
	
	@PostMapping(UrlConstants.CREATE_NEW_AIRPORT)
	public ResponseEntity<JsonResponseEntity<AirportModel>> createNewAirlineEntry(@RequestBody final AirportModel model) {

		JsonResponseEntity<AirportModel> response = new JsonResponseEntity<>();

		final AirportModel newRecord = airportService.createNewEntry(model);
		
		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordSavedSuccessMessage);
		response.setResult(newRecord);
		response.setException(null);
		response.setStatusCode(HttpStatus.CREATED);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping(UrlConstants.FETCH_AIRPORT_BY_CITY)
	public ResponseEntity<JsonResponseEntity<List<AirportModel>>> getAirportsDetailsByCity(
			@PathVariable(value = StringConstants.CITY_CODE) final String cityCode) {

		JsonResponseEntity<List<AirportModel>> response = new JsonResponseEntity<>();

		final List<AirportModel> resultList = airportService.getAirportsCitywise(cityCode);
		
		response.setStatus(StringConstants.success);
		response.setResult(resultList);
		response.setMessage(StringConstants.recordFetchSuccessMessage);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
