package com.flightbooking.flight.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.flight.util.UrlConstants;

@RestController
@RequestMapping(UrlConstants.SEARCH)
public class FlightSearchController {

	@GetMapping("/abc")
	public ResponseEntity<?> getAllFlights() {
		return ResponseEntity.ok("Working");
	}
}
