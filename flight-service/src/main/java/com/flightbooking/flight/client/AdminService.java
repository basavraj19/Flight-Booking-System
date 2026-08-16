package com.flightbooking.flight.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.flightbooking.flight.dto.AirlineResponseModel;
import com.flightbooking.flight.dto.AirportResponseModel;
import com.flightbooking.flight.dto.SeatClassResponseModel;
import com.flightbooking.flight.util.JsonResponseEntity;
import com.flightbooking.flight.util.UrlConstants;

@FeignClient(name = UrlConstants.ADMIN_SERVICE)
public interface AdminService {

	@GetMapping(UrlConstants.GET_AIRPORT_BY_ID)
	public ResponseEntity<JsonResponseEntity<AirportResponseModel>> getAirportDetailsById(
			@PathVariable final Long airportId);

	@GetMapping(UrlConstants.FETCH_AIRPORT_BY_CITY)
	public ResponseEntity<JsonResponseEntity<List<AirportResponseModel>>> getAirportDetailsByCityCode(
			@PathVariable final String cityCode);

	@GetMapping(UrlConstants.FETCH_AIRLINE_BY_ID)
	public ResponseEntity<JsonResponseEntity<List<AirlineResponseModel>>> getAirlineById(
			@RequestParam final List<Long> airlineIds);

	@GetMapping(UrlConstants.FETCH_SEAT_DETILS_SEATS)
	public ResponseEntity<JsonResponseEntity<List<SeatClassResponseModel>>> getSeatDetails();
}
