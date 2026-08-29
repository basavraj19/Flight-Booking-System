package com.flightbooking.flight.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightbooking.flight.dto.FlightScheduleInstanceDetailsModel;
import com.flightbooking.flight.dto.FlightSeatPriceModel;
import com.flightbooking.flight.dto.SeatAvailabilityRequestModel;
import com.flightbooking.flight.dto.SeatAvailabilityResponseModel;
import com.flightbooking.flight.entity.FlightScheduleInstance;
import com.flightbooking.flight.entity.FlightSeatAvailability;
import com.flightbooking.flight.entity.FlightSeatConfiguration;
import com.flightbooking.flight.exception.BusinessException;
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
			List<FlightScheduleInstanceDetailsModel> model, List<FlightScheduleInstance> instances)
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

			FlightScheduleInstanceDetailsModel details = model.get(i);

			List<FlightSeatPriceModel> seatPrices = details.getSeatPrices();

			if (seatPrices == null || seatPrices.isEmpty()) {
				throw new InvalidInputException(
						"Seat prices are required for travel date: " + instance.getTravelDate());
			}

			for (FlightSeatPriceModel priceModel : seatPrices) {

				if (priceModel.getSeatClassId() == null || priceModel.getSeatClassId() <= NumericConstants.ZERO) {

					throw new InvalidInputException("Invalid Seat Class Id.");
				}

				if (priceModel.getPrice() == null || priceModel.getPrice().signum() <= 0) {

					throw new InvalidInputException("Seat price must be greater than zero.");
				}

				Integer totalSeats = seatConfigurationMap.get(priceModel.getSeatClassId());

				if (totalSeats == null) {
					throw new ResourceNotFoundException(
							"Seat Class is not configured for this flight: " + priceModel.getSeatClassId());
				}

				FlightSeatAvailability availability = mapToObject(instance.getId(), priceModel, totalSeats);

				seatAvailabilityList.add(availability);
			}
		}

		flightSeatAvailabilityRepository.saveAll(seatAvailabilityList);
	}

	private FlightSeatAvailability mapToObject(final Long flightScheduleInstanceId, final FlightSeatPriceModel model,
			final int totalSeats) {

		return FlightSeatAvailability.builder().flightScheduleInstanceId(flightScheduleInstanceId)
				.seatClassId(model.getSeatClassId()).totalSeats(totalSeats).bookedSeats(NumericConstants.ZERO)
				.price(model.getPrice()).build();
	}

	@Transactional(readOnly = true)
	public List<FlightSeatAvailability> getSeatAvailabilityByInstanceIds(final List<Long> flightScheduleInstanceIds) {

		if (flightScheduleInstanceIds == null || flightScheduleInstanceIds.isEmpty()) {
			throw new InvalidInputException("Flight Schedule Instance Ids are required.");
		}

		return flightSeatAvailabilityRepository.findByFlightScheduleInstanceIdIn(flightScheduleInstanceIds);
	}

	@Transactional
	public SeatAvailabilityResponseModel reserve(final SeatAvailabilityRequestModel model) {

		if (model == null) {
			throw new InvalidInputException("Invalid Request.");
		}

		if (model.getNumberOfSeats() <= 0) {
			throw new InvalidInputException("Number of seats must be greater than zero.");
		}

		int updatedRows = flightSeatAvailabilityRepository.reserveSeats(model.getFlightScheduleInstanceId(),
				model.getSeatClassId(), model.getNumberOfSeats());

		if (updatedRows == 0) {
			throw new BusinessException("Insufficient seats available.");
		}

		FlightSeatAvailability availability = flightSeatAvailabilityRepository
				.findByFlightScheduleInstanceIdAndSeatClassId(model.getFlightScheduleInstanceId(),
						model.getSeatClassId())
				.get();

		return SeatAvailabilityResponseModel.builder()
				.flightScheduleInstanceId(availability.getFlightScheduleInstanceId())
				.seatClassId(availability.getSeatClassId()).numberOfSeats(model.getNumberOfSeats())
				.availableSeats(availability.getTotalSeats() - availability.getBookedSeats())
				.pricePerSeat(availability.getPrice()).build();
	}
}
