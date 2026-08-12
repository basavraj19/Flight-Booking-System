package com.flightbooking.flight.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.flight.dto.FlightPriceRequestModel;
import com.flightbooking.flight.dto.FlightScheduleResponse;
import com.flightbooking.flight.dto.FlightSeatInventoryRequestModel;
import com.flightbooking.flight.service.FlightSeatInventoryService;
import com.flightbooking.flight.util.JsonResponseEntity;
import com.flightbooking.flight.util.StringConstants;
import com.flightbooking.flight.util.UrlConstants;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(UrlConstants.SEAT_INVENTORY)
@RequiredArgsConstructor
public class FlightSeatInventoryController {

	private final FlightSeatInventoryService flightSeatInventoryService;

	@PostMapping(UrlConstants.CREATE)
	public ResponseEntity<JsonResponseEntity<?>> createFlightSeatInventoryEntry(
			@RequestBody FlightSeatInventoryRequestModel request) {

		JsonResponseEntity<FlightScheduleResponse> response = new JsonResponseEntity<>();

		final boolean newSchedule = flightSeatInventoryService.createNewFlightSeatInventoryEntry(request);

		if (newSchedule) {

			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.recordSavedSuccessMessage);
			response.setResult(null);
			response.setException(null);
			response.setStatusCode(HttpStatus.CREATED);

			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}

		response.setStatus(StringConstants.failed);
		response.setMessage(StringConstants.recordSaveFailedMessage);
		response.setResult(null);
		response.setException(null);
		response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping(UrlConstants.UPDATE_FLIGHT_PRICE)
	public ResponseEntity<JsonResponseEntity<?>> updateFlightPrice(@RequestBody FlightPriceRequestModel request) {

		JsonResponseEntity<FlightScheduleResponse> response = new JsonResponseEntity<>();

		final boolean newSchedule = flightSeatInventoryService.updateFlightPrice(request);

		if (newSchedule) {

			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.recordUpdateSuccessMessage);
			response.setResult(null);
			response.setException(null);
			response.setStatusCode(HttpStatus.OK);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		response.setStatus(StringConstants.failed);
		response.setMessage(StringConstants.recordUpdateFailedMessage);
		response.setResult(null);
		response.setException(null);
		response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
