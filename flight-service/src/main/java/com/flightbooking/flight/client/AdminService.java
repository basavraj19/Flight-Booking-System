package com.flightbooking.flight.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.flightbooking.flight.dto.AirportResponseModel;
import com.flightbooking.flight.util.JsonResponseEntity;
import com.flightbooking.flight.util.UrlConstants;

@FeignClient(name = UrlConstants.ADMIN_SERVICE)
public interface AdminService {

	@GetMapping(UrlConstants.GET_AIRPORT_BY_ID)
	public JsonResponseEntity<AirportResponseModel> getAirportDetailsById(@PathVariable final Long airportId);
}
