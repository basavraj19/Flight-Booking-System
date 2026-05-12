package com.flightbooking.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.admin.dto.CityModel;
import com.flightbooking.admin.entity.City;
import com.flightbooking.admin.service.CityService;
import com.flightbooking.admin.util.JsonResponseEntity;
import com.flightbooking.admin.util.StringConstants;
import com.flightbooking.admin.util.UrlConstants;

@RestController
@RequestMapping(UrlConstants.CITY)
public class CityController {

	@Autowired
	private CityService cityService;

	@PostMapping(UrlConstants.CREATE_NEW_CITY)
	public ResponseEntity<JsonResponseEntity<City>> createNewCity(@RequestBody CityModel city) {

		JsonResponseEntity<City> response = new JsonResponseEntity<>();

		final City newCity = cityService.createNewCityEntry(city);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordSavedSuccessMessage);
		response.setResult(newCity);
		response.setException(null);
		response.setStatusCode(HttpStatus.CREATED);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}
