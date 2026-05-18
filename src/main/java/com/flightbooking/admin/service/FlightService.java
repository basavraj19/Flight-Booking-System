package com.flightbooking.admin.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightbooking.admin.dto.FlightModel;
import com.flightbooking.admin.entity.Flight;
import com.flightbooking.admin.exception.DuplicateResourceException;
import com.flightbooking.admin.exception.InvalidInputException;
import com.flightbooking.admin.exception.ResourceNotFoundException;
import com.flightbooking.admin.repository.FlightRepository;
import com.flightbooking.admin.util.CommonUtils;
import com.flightbooking.admin.util.NumericConstants;
import com.flightbooking.admin.util.StringConstants;

@Service
public class FlightService {

	@Autowired
	private FlightRepository flightRepository;

	@Transactional
	public FlightModel createNewFlightEntry(final FlightModel model) {

		if (model.getAirlineId() == null || model.getAirlineId() <= NumericConstants.ZERO) {
			throw new InvalidInputException("Invalid AirlineId.");
		}

		String flightNumber = validateAndNormalizeFlightNumber(model.getFlightNumber());

		boolean isFlightExists = flightRepository.existsByFlightNumberIgnoreCase(flightNumber);

		if (isFlightExists) {
			throw new DuplicateResourceException("Flight with number " + flightNumber + " already exists.");
		}

		Flight newRecord = Flight.builder().flightNumber(flightNumber).airlineId(model.getAirlineId())
				.createdBy(model.getCreatedBy()).modifiedBy(model.getModifiedBy()).build();

		newRecord = flightRepository.save(newRecord);

		FlightModel newFlight = mapToFlightModel(newRecord);

		return newFlight;
	}

	private String validateAndNormalizeFlightNumber(String flightNumber) {

		if (flightNumber == null || flightNumber.trim().isEmpty()) {
			throw new InvalidInputException("Invalid Flight Number.");
		}

		flightNumber = flightNumber.trim().toUpperCase();

		if (!Pattern.matches(StringConstants.flightNumberRegex, flightNumber)) {
			throw new InvalidInputException("Invalid Flight Number format.");
		}

		return flightNumber;
	}

	private FlightModel mapToFlightModel(final Flight flight) {

		FlightModel model = new FlightModel();

		if (CommonUtils.isValid(flight)) {
			model.setFlightId(flight.getId());
			model.setFlightNumber(flight.getFlightNumber());
			model.setAirlineId(flight.getAirlineId());
			model.setCreatedBy(flight.getCreatedBy());
			model.setModifiedBy(flight.getModifiedBy());
		}

		return model;
	}

	@Transactional(readOnly = true)
	public FlightModel getDetailsByFlightNumber(String flightNumber) {

		flightNumber = validateAndNormalizeFlightNumber(flightNumber);

		Flight flight = flightRepository.findByFlightNumber(flightNumber);

		if (flight == null) {
			throw new ResourceNotFoundException("Flight with number " + flightNumber + " does not exist.");
		}

		FlightModel model = mapToFlightModel(flight);

		return model;
	}

	@Transactional(readOnly = true)
	public List<FlightModel> getAllFlightsByAirline(Long airlineId) {

		if (airlineId <= NumericConstants.ZERO) {
			throw new InvalidInputException("Invalid AirlineId.");
		}

		List<Flight> flights = flightRepository.findByAirlineId(airlineId);

		if (!CommonUtils.isValid(flights)) {
			throw new ResourceNotFoundException("No Records found.");
		}

		List<FlightModel> dataList = flights.stream().map(this::mapToFlightModel).toList();

		return dataList;
	}
}
