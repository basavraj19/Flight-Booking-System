package com.example.Flight.Booking.System.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Flight.Booking.System.dto.CityModel;
import com.example.Flight.Booking.System.entity.City;
import com.example.Flight.Booking.System.service.CityService;
import com.example.Flight.Booking.System.util.CommonUtils;
import com.example.Flight.Booking.System.util.JsonResponseEntity;
import com.example.Flight.Booking.System.util.StringConstants;
import com.example.Flight.Booking.System.util.UrlConstants;

@RestController
@RequestMapping(UrlConstants.CITY)
public class CityController {

	@Autowired
	private CityService cityService;

	
	public JsonResponseEntity<City> createNewCity(@RequestBody CityModel city) throws Exception {

		JsonResponseEntity<City> response = new JsonResponseEntity<>();
		final City newCity = cityService.createNewCityEntry(city);

		if (CommonUtils.isValid(newCity)) {
			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.recordSavedSuccessMessage);
			response.setResult(newCity);
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
