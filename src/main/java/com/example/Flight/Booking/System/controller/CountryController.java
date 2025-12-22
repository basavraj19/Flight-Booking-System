package com.example.Flight.Booking.System.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Flight.Booking.System.dto.CountryRequestDto;
import com.example.Flight.Booking.System.entity.Country;
import com.example.Flight.Booking.System.service.CountryService;
import com.example.Flight.Booking.System.util.CommonUtils;
import com.example.Flight.Booking.System.util.JsonResponseEntity;
import com.example.Flight.Booking.System.util.StringConstants;
import com.example.Flight.Booking.System.util.UrlConstants;

@RestController
@RequestMapping("/country")
public class CountryController {

	@Autowired
	private CountryService countryService;

	@PostMapping(UrlConstants.createNewCountry)
	public JsonResponseEntity<Country> createNewEntry(final @RequestBody CountryRequestDto record) throws Exception {
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
}
