package com.flightbooking.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.flightbooking.booking.util.JsonResponseEntity;
import com.flightbooking.booking.util.UrlConstants;

@FeignClient(name = UrlConstants.AUTH_SERVICE)
public interface AuthService {

	@GetMapping(UrlConstants.GET_USER_BY_USERNAME)
	public ResponseEntity<JsonResponseEntity<Long>> getUserByUsername(@PathVariable final String username);
}
