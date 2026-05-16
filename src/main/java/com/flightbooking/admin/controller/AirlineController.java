package com.flightbooking.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.admin.dto.AirlineModel;
import com.flightbooking.admin.entity.Airline;
import com.flightbooking.admin.service.AirlineService;
import com.flightbooking.admin.util.JsonResponseEntity;
import com.flightbooking.admin.util.StringConstants;
import com.flightbooking.admin.util.UrlConstants;

@RestController
@RequestMapping(UrlConstants.AIRLINE)
public class AirlineController {

	@Autowired
	private AirlineService airlineService;

	@PostMapping(UrlConstants.CREATE_NEW_AIRLINE)
	public ResponseEntity<JsonResponseEntity<Airline>> createNewAirlineEntry(@RequestBody final AirlineModel model) {

		JsonResponseEntity<Airline> response = new JsonResponseEntity<>();

		Airline newRecord = airlineService.createNewEntry(model);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordSavedSuccessMessage);
		response.setResult(newRecord);
		response.setException(null);
		response.setStatusCode(HttpStatus.CREATED);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
