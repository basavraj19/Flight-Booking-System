package com.flightbooking.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.flightbooking.booking.dto.FlightScheduleInstanceResponseModel;
import com.flightbooking.booking.dto.SeatAvailabilityRequestModel;
import com.flightbooking.booking.dto.SeatAvailabilityResponseModel;
import com.flightbooking.booking.util.JsonResponseEntity;
import com.flightbooking.booking.util.UrlConstants;

@FeignClient(name = UrlConstants.FLIGHT_SERVICE)
public interface FlightService {

	@GetMapping(UrlConstants.FETCH_FLIGHT_SCHDEULE_INSTANCE_BY_ID)
	public ResponseEntity<JsonResponseEntity<FlightScheduleInstanceResponseModel>> getFlightInstanceById(
			@PathVariable final Long id);

	@PostMapping(UrlConstants.RESERVE_SEATS)
	public ResponseEntity<JsonResponseEntity<SeatAvailabilityResponseModel>> reserveSeats(
			@RequestBody SeatAvailabilityRequestModel model);

	@PostMapping(UrlConstants.RESERVE_SEATS)
	public ResponseEntity<JsonResponseEntity<Void>> releaseSeats(@RequestBody SeatAvailabilityRequestModel model);
}
