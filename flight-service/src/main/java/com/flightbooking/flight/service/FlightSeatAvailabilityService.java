package com.flightbooking.flight.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightbooking.flight.dto.FlightScheduleInstanceDetailsRequestModel;
import com.flightbooking.flight.dto.FlightSeatPriceRequestModel;
import com.flightbooking.flight.entity.FlightScheduleInstance;
import com.flightbooking.flight.entity.FlightSeatAvailability;
import com.flightbooking.flight.entity.FlightSeatConfiguration;
import com.flightbooking.flight.exception.InvalidInputException;
import com.flightbooking.flight.exception.ResourceNotFoundException;
import com.flightbooking.flight.repository.FlightScheduleRepository;
import com.flightbooking.flight.repository.FlightSeatAvailabilityRepository;
import com.flightbooking.flight.util.NumericConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightSeatAvailabilityService {

	private final FlightScheduleRepository flightScheduleRepository;

	private final FlightSeatConfigurationService flightSeatConfigurationService;

	private final FlightSeatAvailabilityRepository flightSeatAvailabilityRepository;

	@Transactional
	public void createNewFlightSeatInventoryEntry(final Long flightScheduleId,
			List<FlightScheduleInstanceDetailsRequestModel> model, List<FlightScheduleInstance> instances)
			throws InvalidInputException, ResourceNotFoundException {

		final Long flightId = flightScheduleRepository.getFlightIdByFlightScheduleId(flightScheduleId);

		if (flightId == null || flightId <= NumericConstants.ZERO) {
			throw new ResourceNotFoundException("Flight Schedule not found.");
		}

		final List<FlightSeatConfiguration> seatDetails = flightSeatConfigurationService
				.getFlightSeatConfigurationByFlightId(flightId);

		if (seatDetails.isEmpty()) {
			throw new ResourceNotFoundException("No seat configuration found for the flight.");
		}

		Map<Long, Integer> seatConfigurationMap = new HashMap<>();

		for (FlightSeatConfiguration seat : seatDetails) {
			seatConfigurationMap.put(seat.getSeatClassId(), seat.getTotalSeats());
		}

		List<FlightSeatAvailability> seatAvailabilityList = new ArrayList<>();

		// Creating seat availability for each instance
		for (int i = 0; i < instances.size(); i++) {

			FlightScheduleInstance instance = instances.get(i);

			FlightScheduleInstanceDetailsRequestModel details = model.get(i);

			List<FlightSeatPriceRequestModel> seatPrices = details.getSeatPrices();

			if (seatPrices == null || seatPrices.isEmpty()) {
				throw new InvalidInputException(
						"Seat prices are required for travel date: " + instance.getTravelDate());
			}

			for (FlightSeatPriceRequestModel priceModel : seatPrices) {

				if (priceModel.getSeatTypeId() == null || priceModel.getSeatTypeId() <= NumericConstants.ZERO) {

					throw new InvalidInputException("Invalid Seat Class Id.");
				}

				if (priceModel.getPrice() == null || priceModel.getPrice().signum() <= 0) {

					throw new InvalidInputException("Seat price must be greater than zero.");
				}

				Integer totalSeats = seatConfigurationMap.get(priceModel.getSeatTypeId());

				if (totalSeats == null) {
					throw new ResourceNotFoundException(
							"Seat Class is not configured for this flight: " + priceModel.getSeatTypeId());
				}

				FlightSeatAvailability availability = mapToObject(instance.getId(), priceModel, totalSeats);

				seatAvailabilityList.add(availability);
			}
		}

		flightSeatAvailabilityRepository.saveAll(seatAvailabilityList);
	}

	private FlightSeatAvailability mapToObject(final Long flightScheduleInstanceId,
			final FlightSeatPriceRequestModel model, final int totalSeats) {

		return FlightSeatAvailability.builder().flightScheduleInstanceId(flightScheduleInstanceId)
				.seatClassId(model.getSeatTypeId()).totalSeats(totalSeats).bookedSeats(NumericConstants.ZERO)
				.price(model.getPrice()).build();
	}
}
