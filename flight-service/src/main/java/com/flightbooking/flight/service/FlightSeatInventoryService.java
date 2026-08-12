package com.flightbooking.flight.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightbooking.flight.dto.FlightPriceRequestModel;
import com.flightbooking.flight.dto.FlightSeatInventoryRequestModel;
import com.flightbooking.flight.entity.FlightSeatConfiguration;
import com.flightbooking.flight.entity.FlightSeatInventory;
import com.flightbooking.flight.exception.DuplicateResourceException;
import com.flightbooking.flight.exception.InvalidInputException;
import com.flightbooking.flight.exception.ResourceNotFoundException;
import com.flightbooking.flight.repository.FlightScheduleRepository;
import com.flightbooking.flight.repository.FlightSeatInventoryRepository;
import com.flightbooking.flight.util.NumericConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightSeatInventoryService {

	private final FlightScheduleRepository flightScheduleRepository;

	private final FlightSeatConfigurationService flightSeatConfigurationService;

	private final FlightSeatInventoryRepository flightSeatInventoryRepository;

	@Transactional
	public Boolean createNewFlightSeatInventoryEntry(final FlightSeatInventoryRequestModel model)
			throws InvalidInputException, ResourceNotFoundException {

		if (model == null) {
			throw new InvalidInputException("Invalid Request Object.");
		}

		final Long flightId = flightScheduleRepository.getFlightIdByFlightScheduleId(model.getFlightScheduleId());

		if (flightId == null || flightId <= NumericConstants.ZERO) {
			throw new InvalidInputException("Invalid Flight Schedule Id.");
		}

		final List<FlightSeatConfiguration> seatDetails = flightSeatConfigurationService
				.getFlightSeatConfigurationByFlightId(flightId);

		if (seatDetails.isEmpty()) {
			throw new ResourceNotFoundException("Invalid Flight Id.");
		}

		boolean isEntryPresent = flightSeatInventoryRepository
				.existsByFlightScheduleIdAndTravelDate(model.getFlightScheduleId(), model.getTravelDate());

		if (isEntryPresent) {
			throw new DuplicateResourceException("Seat inventory already exists for the given travel date.");
		}

		List<FlightSeatInventory> seatInventoryList = new ArrayList<>();

		for (FlightSeatConfiguration seats : seatDetails) {

			FlightSeatInventory entry = mapToObject(model, seats);
			seatInventoryList.add(entry);
		}

		flightSeatInventoryRepository.saveAll(seatInventoryList);

		return true;
	}

	private FlightSeatInventory mapToObject(final FlightSeatInventoryRequestModel model,
			FlightSeatConfiguration seatDetails) {

		return FlightSeatInventory.builder().FlightScheduleId(model.getFlightScheduleId())
				.seatClassId(seatDetails.getSeatClassId()).travelDate(model.getTravelDate())
				.totalSeats(seatDetails.getTotalSeats()).bookedSeats(NumericConstants.ZERO).price(model.getPrice())
				.build();
	}

	@Transactional
	public Boolean updateFlightPrice(final FlightPriceRequestModel model) {

		if (model == null) {
			throw new InvalidInputException("Invalid Request Object.");
		}

		FlightSeatInventory entry = flightSeatInventoryRepository
				.findByFlightScheduleIdSeatClassIdAndTravelDate(model.getFlightScheduleId(), model.getSeatTypeId(),
						model.getTravelDate())
				.orElseThrow(() -> new InvalidInputException("Flight Schedule not found."));

		entry.setPrice(model.getPrice());

		flightSeatInventoryRepository.save(entry);

		return true;
	}
}
