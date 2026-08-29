package com.flightbooking.flight.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.flight.dto.FlightScheduleInstanceRequestModel;
import com.flightbooking.flight.dto.FlightScheduleInstanceResponseModel;
import com.flightbooking.flight.dto.UpdateFlightPriceRequestModel;
import com.flightbooking.flight.dto.UpdateFlightScheduleInstanceRequestModel;
import com.flightbooking.flight.dto.UpdateFlightScheduleInstanceStatusRequestModel;
import com.flightbooking.flight.exception.DuplicateResourceException;
import com.flightbooking.flight.exception.InvalidInputException;
import com.flightbooking.flight.exception.ResourceNotFoundException;
import com.flightbooking.flight.service.FlightScheduleInstanceService;
import com.flightbooking.flight.util.JsonResponseEntity;
import com.flightbooking.flight.util.StringConstants;
import com.flightbooking.flight.util.UrlConstants;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(UrlConstants.FLIGHT_SCHEDULE_INSTANCE)
@RequiredArgsConstructor
public class FlightScheduleInstanceController {

	private final FlightScheduleInstanceService flightScheduleInstanceService;

	@PostMapping(UrlConstants.CREATE_FLIGHT_SCHEDULE_INSTANCE)
	public ResponseEntity<JsonResponseEntity<Boolean>> createFlightScheduleInstances(
			@RequestBody final FlightScheduleInstanceRequestModel model)
			throws InvalidInputException, ResourceNotFoundException, DuplicateResourceException {

		JsonResponseEntity<Boolean> response = new JsonResponseEntity<>();

		final Boolean result = flightScheduleInstanceService.createFlightScheduleInstances(model);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordSavedSuccessMessage);
		response.setResult(result);
		response.setException(null);
		response.setStatusCode(HttpStatus.CREATED);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping(UrlConstants.UPDATE_FLIGHT_SCHEDULE_INSTANCE)
	public ResponseEntity<JsonResponseEntity<Boolean>> updateTravelDate(@PathVariable final Long id,
			@RequestBody final UpdateFlightScheduleInstanceRequestModel model) {

		JsonResponseEntity<Boolean> response = new JsonResponseEntity<>();

		final Boolean result = flightScheduleInstanceService.updateTravelDate(id, model);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordUpdateSuccessMessage);
		response.setResult(result);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping(UrlConstants.UPDATE_FLIGHT_SCHEDULE_INSTANCE_STATUS)
	public ResponseEntity<JsonResponseEntity<Boolean>> updateStatus(@PathVariable final Long id,
			@RequestBody final UpdateFlightScheduleInstanceStatusRequestModel model) {

		JsonResponseEntity<Boolean> response = new JsonResponseEntity<>();

		final Boolean result = flightScheduleInstanceService.updateStatus(id, model);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordUpdateSuccessMessage);
		response.setResult(result);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping(UrlConstants.UPDATE_FLIGHT_SCHEDULE_INSTANCE_PRICE)
	public ResponseEntity<JsonResponseEntity<Boolean>> updatePrice(
			@RequestBody final UpdateFlightPriceRequestModel model) {

		JsonResponseEntity<Boolean> response = new JsonResponseEntity<>();

		final Boolean result = flightScheduleInstanceService.updateFlightPrice(model);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordUpdateSuccessMessage);
		response.setResult(result);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(UrlConstants.FETCH_FLIGHT_SCHEDULE_INSTANCE_BY_ID)
	public ResponseEntity<JsonResponseEntity<FlightScheduleInstanceResponseModel>> getFlightScheduleInstanceById(
			@PathVariable(value = StringConstants.Id) final Long id) {

		JsonResponseEntity<FlightScheduleInstanceResponseModel> response = new JsonResponseEntity<>();

		final FlightScheduleInstanceResponseModel result = flightScheduleInstanceService
				.getFlightScheduleInstanceById(id);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordUpdateSuccessMessage);
		response.setResult(result);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}