package com.flightbooking.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.admin.dto.CountryModel;
import com.flightbooking.admin.entity.Country;
import com.flightbooking.admin.service.CountryService;
import com.flightbooking.admin.util.JsonResponseEntity;
import com.flightbooking.admin.util.StringConstants;
import com.flightbooking.admin.util.UrlConstants;

@RestController
@RequestMapping(UrlConstants.COUNTRY)
public class CountryController {

	@Autowired
	private CountryService countryService;

	@PostMapping(UrlConstants.CREATE_NEW_COUNTRY)
	public ResponseEntity<JsonResponseEntity<Country>> createNewEntry(final @RequestBody CountryModel record) {
		JsonResponseEntity<Country> response = new JsonResponseEntity<>();

		Country newCountry = countryService.saveNewCountryDetails(record);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordSavedSuccessMessage);
		response.setResult(newCountry);
		response.setException(null);
		response.setStatusCode(HttpStatus.CREATED);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping(UrlConstants.FETCH_COUNTRY_DETAILS)
	public ResponseEntity<JsonResponseEntity<Country>> getCountryDetails(
			@RequestParam(value = StringConstants.COUNTRY_CODE) final String countryCode) {
		JsonResponseEntity<Country> response = new JsonResponseEntity<>();

		Country record = countryService.getCountryDetailsByCountryCode(countryCode);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordFetchSuccessMessage);
		response.setResult(record);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(UrlConstants.FETCH_ALL_COUNTRY_DETAILS)
	public ResponseEntity<JsonResponseEntity<List<Country>>> getAllCountryDetails() {
		JsonResponseEntity<List<Country>> response = new JsonResponseEntity<>();

		List<Country> result = countryService.getAllCountryDetails();

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordFetchSuccessMessage);
		response.setResult(result);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping(UrlConstants.UPDATE_COUNTRY_DETAILS)
	public ResponseEntity<JsonResponseEntity<Country>> updateCountryDetails(final @RequestBody CountryModel record) {
		JsonResponseEntity<Country> response = new JsonResponseEntity<>();

		Country updatedCountry = countryService.updateDetails(record);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordUpdateSuccessMessage);
		response.setResult(updatedCountry);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
