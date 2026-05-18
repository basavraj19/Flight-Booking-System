package com.flightbooking.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.flightbooking.admin.dto.AirportRequestModel;
import com.flightbooking.admin.dto.AirportResponseModel;
import com.flightbooking.admin.dto.CityResponseModel;
import com.flightbooking.admin.entity.Airport;
import com.flightbooking.admin.exception.DuplicateResourceException;
import com.flightbooking.admin.exception.InvalidInputException;
import com.flightbooking.admin.exception.ResourceNotFoundException;
import com.flightbooking.admin.repository.AirportRepository;
import com.flightbooking.admin.util.CommonUtils;
import com.flightbooking.admin.util.NumericConstants;

@Service
public class AirportService {

	@Autowired
	private AirportRepository airportRepository;

	@Autowired
	private CityService cityService;

	@Transactional
	public AirportResponseModel createNewEntry(final AirportRequestModel model) {

		String airportCode = model.getAirportCode().trim().toUpperCase();

		if (!StringUtils.hasText(airportCode)
				|| !(airportCode.length() >= NumericConstants.TWO && airportCode.length() <= NumericConstants.THREE)) {
			throw new InvalidInputException("Invalid Airport Code.");
		}

		if (!StringUtils.hasText(model.getAirportName())) {
			throw new InvalidInputException("Invalid Airline name.");
		}

		boolean existingRecord = airportRepository.existsByAirportCode(airportCode);

		if (existingRecord) {
			throw new DuplicateResourceException("Airport Already Exists.");
		}

		Airport newAirport = Airport.builder().airportCode(airportCode).airportName(model.getAirportName().trim())
				.cityId(model.getCityId()).build();

		newAirport = airportRepository.save(newAirport);

		final AirportResponseModel newEntry = mapToAirportModel(newAirport);

		return newEntry;
	}

	private AirportResponseModel mapToAirportModel(final Airport airport) {

		final AirportResponseModel model = new AirportResponseModel();

		if (CommonUtils.isValid(airport)) {
			model.setRecordId(airport.getId());
			model.setAirportCode(airport.getAirportCode());
			model.setAirportName(airport.getAirportName());
			model.setCityId(airport.getCityId());
			;
			model.setCreatedBy(airport.getCreatedBy());
			model.setModifiedBy(airport.getModifiedBy());
		}
		return model;
	}

	@Transactional(readOnly = true)
	public List<AirportResponseModel> getAirportsCitywise(final String cityCode) {
		final CityResponseModel model = cityService.getCityDetailsByCode(cityCode);

		final List<Airport> airportList = airportRepository.findAllByCityId(model.getRecordId());

		if (!CommonUtils.isValid(airportList)) {
			throw new ResourceNotFoundException("No Records found.");
		}

		List<AirportResponseModel> resultSet = airportList.stream().map(this::mapToAirportModel).toList();

		return resultSet;
	}
}
