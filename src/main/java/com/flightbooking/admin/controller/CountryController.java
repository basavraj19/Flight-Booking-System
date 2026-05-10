package com.flightbooking.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.flightbooking.admin.util.CommonUtils;
import com.flightbooking.admin.util.JsonResponseEntity;
import com.flightbooking.admin.util.StringConstants;
import com.flightbooking.admin.util.UrlConstants;

@RestController
@RequestMapping(UrlConstants.COUNTRY)
public class CountryController {

	@Autowired
	private CountryService countryService;

	@PostMapping(UrlConstants.CREATE_NEW_COUNTRY)
	public JsonResponseEntity<Country> createNewEntry(final @RequestBody CountryModel record) throws Exception {
		JsonResponseEntity<Country> response = new JsonResponseEntity<>();

		Country newCountry = countryService.saveNewCountryDetails(record);

		if (CommonUtils.isValid(newCountry)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.recordSavedSuccessMessage);
			response.setResult(newCountry);
			response.setException(null);
			response.setStatusCode(HttpStatus.CREATED);
		} else {
			response.setStatus(StringConstants.failed);
			response.setMessage(StringConstants.recordSaveFailedMessage);
			response.setResult(null);
			response.setException(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@GetMapping(UrlConstants.FETCH_COUNTRY_DETAILS)
	public JsonResponseEntity<Country> getCountryDetails(@RequestParam final String countryCode) throws Exception {
		JsonResponseEntity<Country> response = new JsonResponseEntity<>();

		Country record = countryService.getCountryDetailsByCountryCode(countryCode);

		if (CommonUtils.isValid(record)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.recordFetchSuccessMessage);
			response.setResult(record);
			response.setException(null);
			response.setStatusCode(HttpStatus.OK);
		} else {
			response.setStatus(StringConstants.failed);
			response.setMessage(StringConstants.requestFailedMessage);
			response.setResult(null);
			response.setException(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@PatchMapping(UrlConstants.UPDATE_COUNTRY_DETAILS)
	public JsonResponseEntity<Country> updateCountryDetails(final @RequestBody CountryModel record) throws Exception {
		JsonResponseEntity<Country> response = new JsonResponseEntity<>();

		Country updatedCountry = countryService.updateDetails(record);

		if (CommonUtils.isValid(updatedCountry)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.recordUpdateSuccessMessage);
			response.setResult(updatedCountry);
			response.setException(null);
			response.setStatusCode(HttpStatus.OK);
		} else {
			response.setStatus(StringConstants.failed);
			response.setMessage(StringConstants.recordUpdateFailedMessage);
			response.setResult(null);
			response.setException(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
}
