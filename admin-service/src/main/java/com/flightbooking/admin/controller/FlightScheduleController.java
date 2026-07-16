package com.flightbooking.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.admin.dto.FlightScheduleRequest;
import com.flightbooking.admin.dto.FlightScheduleResponse;
import com.flightbooking.admin.service.FlightScheduleService;
import com.flightbooking.admin.util.JsonResponseEntity;
import com.flightbooking.admin.util.StringConstants;
import com.flightbooking.admin.util.UrlConstants;

@RestController
@RequestMapping(UrlConstants.FLIGHT_SCHEDULE)
public class FlightScheduleController {

	@Autowired
	private FlightScheduleService flightScheduleService;

	@PostMapping(UrlConstants.CREATE_NEW_FLIGHT_SCHEDULE_ENTRY)
	public ResponseEntity<JsonResponseEntity<FlightScheduleResponse>> createNewFlightSchedule(
			@RequestBody final FlightScheduleRequest request) {

		JsonResponseEntity<FlightScheduleResponse> response = new JsonResponseEntity<>();

		final FlightScheduleResponse newSchedule = flightScheduleService.createNewFlightScheduleEntry(request);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordSavedSuccessMessage);
		response.setResult(newSchedule);
		response.setException(null);
		response.setStatusCode(HttpStatus.CREATED);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping(UrlConstants.UPDATE_FLIGHT_SCHEDULE_DETAILS)
	public ResponseEntity<JsonResponseEntity<FlightScheduleResponse>> updateFlightSchedule(
			@RequestBody final FlightScheduleRequest model) {

		JsonResponseEntity<FlightScheduleResponse> response = new JsonResponseEntity<>();

		final FlightScheduleResponse updatedSchedule = flightScheduleService.updateFlightSchedule(model);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordUpdateSuccessMessage);
		response.setResult(updatedSchedule);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
