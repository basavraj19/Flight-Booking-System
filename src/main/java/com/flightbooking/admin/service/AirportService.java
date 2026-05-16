package com.flightbooking.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.flightbooking.admin.entity.Airport;
import com.flightbooking.admin.exception.DuplicateResourceException;
import com.flightbooking.admin.exception.InvalidInputException;
import com.flightbooking.admin.repository.AirportRepository;
import com.flightbooking.admin.util.NumericConstants;

@Service
public class AirportService {

	@Autowired
	private AirportRepository airportRepository;

	@Transactional
	public Airport createNewEntry(final Airport model) {

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

		return airportRepository.save(newAirport);
	}
}
