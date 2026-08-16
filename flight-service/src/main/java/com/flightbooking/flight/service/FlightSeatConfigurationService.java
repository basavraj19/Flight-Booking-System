package com.flightbooking.flight.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightbooking.flight.client.AdminService;
import com.flightbooking.flight.dto.FlightSeatConfigurationRequestModel;
import com.flightbooking.flight.dto.SeatClassResponseModel;
import com.flightbooking.flight.entity.FlightSeatConfiguration;
import com.flightbooking.flight.exception.DuplicateResourceException;
import com.flightbooking.flight.exception.InvalidInputException;
import com.flightbooking.flight.exception.ResourceNotFoundException;
import com.flightbooking.flight.repository.FlightRepository;
import com.flightbooking.flight.repository.FlightSeatConfigurationRepository;
import com.flightbooking.flight.util.NumericConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightSeatConfigurationService {

	private final FlightSeatConfigurationRepository flightSeatConfigurationRepository;

	private final FlightRepository flightRepository;

	private final AdminService adminService;

	@Transactional
	public Boolean createFlightSeatConfiguration(final FlightSeatConfigurationRequestModel model)
			throws InvalidInputException, ResourceNotFoundException, DuplicateResourceException {

		ValidateRequestModel(model);

		if (!flightRepository.existsById(model.getFlightId())) {
			throw new ResourceNotFoundException("Flight not found.");
		}

		boolean exists = flightSeatConfigurationRepository.existsByFlightIdAndSeatClassId(model.getFlightId(),
				model.getSeatClassId());

		if (exists) {
			throw new DuplicateResourceException("Seat configuration already exists for the given seat class.");
		}

		FlightSeatConfiguration configuration = FlightSeatConfiguration.builder().flightId(model.getFlightId())
				.seatClassId(model.getSeatClassId()).totalSeats(model.getTotalSeats()).build();

		flightSeatConfigurationRepository.save(configuration);

		return true;
	}

	@Transactional(readOnly = true)
	public List<FlightSeatConfiguration> getFlightSeatConfigurationByFlightId(final Long flightId)
			throws ResourceNotFoundException {

		if (flightId == null || flightId <= NumericConstants.ZERO) {
			throw new InvalidInputException("Invalid Flight Id.");
		}

		List<FlightSeatConfiguration> data = flightSeatConfigurationRepository.findByFlightId(flightId);

		return data;
	}

	@Transactional
	public Boolean updateFlightSeatConfiguration(final Long id, final FlightSeatConfigurationRequestModel model)
			throws InvalidInputException, ResourceNotFoundException, DuplicateResourceException {

		if (id == null || id <= NumericConstants.ZERO) {
			throw new InvalidInputException("Invalid Id.");
		}

		ValidateRequestModel(model);

		FlightSeatConfiguration configuration = flightSeatConfigurationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Flight Seat Configuration not found."));

		boolean duplicate = flightSeatConfigurationRepository.existsByFlightIdAndSeatClassId(model.getFlightId(),
				model.getSeatClassId());

		if (duplicate && (!configuration.getFlightId().equals(model.getFlightId())
				|| !configuration.getSeatClassId().equals(model.getSeatClassId()))) {

			throw new DuplicateResourceException("Seat configuration already exists for the given seat class.");
		}

		configuration.setFlightId(model.getFlightId());
		configuration.setSeatClassId(model.getSeatClassId());
		configuration.setTotalSeats(model.getTotalSeats());

		flightSeatConfigurationRepository.save(configuration);

		return true;
	}

	private void ValidateRequestModel(final FlightSeatConfigurationRequestModel model) {
		if (model == null) {
			throw new InvalidInputException("Invalid request object.");
		}

		if (model.getFlightId() == null || model.getFlightId() <= NumericConstants.ZERO) {
			throw new InvalidInputException("Invalid Flight Id.");
		}

		if (model.getSeatClassId() == null || model.getSeatClassId() <= NumericConstants.ZERO) {
			throw new InvalidInputException("Invalid Seat Class Id.");
		}

		if (model.getTotalSeats() <= NumericConstants.ZERO) {
			throw new InvalidInputException("Total seats should be greater than zero.");
		}

		if (!flightRepository.existsById(model.getFlightId())) {
			throw new ResourceNotFoundException("Flight not found.");
		}

		List<SeatClassResponseModel> seatDetails = adminService.getSeatDetails().getBody().getResult();
		boolean exists = seatDetails.stream().anyMatch(seat -> seat.getRecordId().equals(model.getSeatClassId()));

		if (!exists) {
			throw new InvalidInputException("Invalid Seat Class Id.");
		}
	}
}
