package com.flightbooking.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping(UrlConstants.FETCH_CITY_DETAILS)
	public ResponseEntity<JsonResponseEntity<CityModel>> getCityDetails(
			@RequestParam(value = StringConstants.CITY_CODE) final String cityCode) {
		JsonResponseEntity<CityModel> response = new JsonResponseEntity<>();

		CityModel result = cityService.getCityDetailsByCode(cityCode);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordFetchSuccessMessage);
		response.setResult(result);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(UrlConstants.FETCH_CITIES_BY_COUNTRY)
	public ResponseEntity<JsonResponseEntity<List<CityModel>>> getCitieByCountry(
			@RequestParam(value = StringConstants.COUNTRY_CODE) final String countryCode) {
		JsonResponseEntity<List<CityModel>> response = new JsonResponseEntity<>();

		List<CityModel> result = cityService.getCityDetailsByCountry(countryCode);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordFetchSuccessMessage);
		response.setResult(result);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping(UrlConstants.UPDATE_CITY_DETAILS)
	public ResponseEntity<JsonResponseEntity<CityModel>> updateCityDetails(@RequestBody final CityModel request) {
		JsonResponseEntity<CityModel> response = new JsonResponseEntity<>();

		CityModel result = cityService.updateCityDetails(request);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordUpdateSuccessMessage);
		response.setResult(result);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
