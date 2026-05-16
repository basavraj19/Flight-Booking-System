package com.flightbooking.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.flightbooking.admin.dto.AirportModel;
import com.flightbooking.admin.dto.CityModel;
import com.flightbooking.admin.entity.Airport;
import com.flightbooking.admin.exception.DuplicateResourceException;
import com.flightbooking.admin.exception.InvalidInputException;
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
	public AirportModel createNewEntry(final AirportModel model) {

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
				.cityId(model.getCityId()).createdBy(model.getCreatedBy()).modifiedBy(model.getModifiedBy()).build();

		newAirport = airportRepository.save(newAirport);

		final AirportModel newEntry = mapToAirportModel(newAirport);

		return newEntry;
	}

	private AirportModel mapToAirportModel(final Airport airport) {

		final AirportModel model = new AirportModel();

		if (CommonUtils.isValid(airport)) {
			model.setRecordId(airport.getId());
			model.setAirportCode(airport.getAirportCode());
			model.setAirportName(airport.getAirportName());
			model.setCreatedBy(airport.getCreatedBy());
			model.setModifiedBy(airport.getModifiedBy());
		}
		return model;
	}

	@Transactional
	public List<AirportModel> getAirportsCitywise(final String cityCode) {
		final CityModel model = cityService.getCityDetailsByCode(cityCode);

		final List<AirportModel> resultSet = new ArrayList<>();

		final List<Airport> airportList = airportRepository.findAllByCityId(model.getRecordId());

		if (CommonUtils.isValid(airportList)) {

			for (Airport record : airportList) {
				final AirportModel newEntry = mapToAirportModel(record);
				resultSet.add(newEntry);
			}
		}
		
		return resultSet;
	}
}
