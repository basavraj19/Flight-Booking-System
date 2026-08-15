package com.flightbooking.flight.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightbooking.flight.dto.FlightScheduleInstanceDetailsRequestModel;
import com.flightbooking.flight.dto.FlightScheduleInstanceRequestModel;
import com.flightbooking.flight.dto.UpdateFlightPriceRequestModel;
import com.flightbooking.flight.dto.UpdateFlightScheduleInstanceRequestModel;
import com.flightbooking.flight.dto.UpdateFlightScheduleInstanceStatusRequestModel;
import com.flightbooking.flight.entity.FlightScheduleInstance;
import com.flightbooking.flight.entity.FlightSeatAvailability;
import com.flightbooking.flight.exception.DuplicateResourceException;
import com.flightbooking.flight.exception.InvalidInputException;
import com.flightbooking.flight.exception.ResourceNotFoundException;
import com.flightbooking.flight.repository.FlightScheduleInstanceRepository;
import com.flightbooking.flight.repository.FlightSeatAvailabilityRepository;
import com.flightbooking.flight.util.FlightStatus;
import com.flightbooking.flight.util.NumericConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightScheduleInstanceService {

	private final FlightSeatAvailabilityService flightSeatAvailabilityService;

	private final FlightScheduleInstanceRepository flightScheduleInstanceRepository;

	private final FlightSeatAvailabilityRepository flightSeatAvailabilityRepository;

	@Transactional
	public Boolean createFlightScheduleInstances(final FlightScheduleInstanceRequestModel model)
			throws InvalidInputException, ResourceNotFoundException, DuplicateResourceException {

		if (model == null) {
			throw new InvalidInputException("Invalid request object.");
		}

		if (model.getFlightScheduleId() == null || model.getFlightScheduleId() <= NumericConstants.ZERO) {
			throw new InvalidInputException("Invalid Flight Schedule Id.");
		}

		if (model.getInstances() == null || model.getInstances().isEmpty()) {
			throw new InvalidInputException("Instance details are required.");
		}

		List<FlightScheduleInstance> instances = new ArrayList<>();

		/*
		 * Create FlightScheduleInstance objects first.
		 */
		for (FlightScheduleInstanceDetailsRequestModel item : model.getInstances()) {

			if (item == null || item.getTravelDate() == null) {
				throw new InvalidInputException("Travel date is required.");
			}

			if (flightScheduleInstanceRepository.existsByFlightScheduleIdAndTravelDate(model.getFlightScheduleId(),
					item.getTravelDate())) {

				throw new DuplicateResourceException(
						"Flight Schedule Instance already exists for travel date: " + item.getTravelDate());
			}

			FlightScheduleInstance instance = FlightScheduleInstance.builder()
					.flightScheduleId(model.getFlightScheduleId()).travelDate(item.getTravelDate())
					.status(FlightStatus.SCHEDULED).build();

			instances.add(instance);
		}

		/*
		 * Save instances first so that IDs are generated.
		 */
		List<FlightScheduleInstance> savedInstances = flightScheduleInstanceRepository.saveAll(instances);

		flightSeatAvailabilityService.createNewFlightSeatInventoryEntry(model.getFlightScheduleId(),
				model.getInstances(), savedInstances);

		return true;
	}

	@Transactional
	public Boolean updateTravelDate(final Long id, final UpdateFlightScheduleInstanceRequestModel model)
			throws InvalidInputException, ResourceNotFoundException, DuplicateResourceException {

		if (id == null || id <= NumericConstants.ZERO) {
			throw new InvalidInputException("Invalid Flight Schedule Instance Id.");
		}

		if (model == null || model.getTravelDate() == null) {
			throw new InvalidInputException("Travel date is required.");
		}

		FlightScheduleInstance instance = flightScheduleInstanceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Flight Schedule Instance not found."));

		boolean exists = flightScheduleInstanceRepository.existsByFlightScheduleIdAndTravelDateAndIdNot(
				instance.getFlightScheduleId(), model.getTravelDate(), id);

		if (exists) {
			throw new DuplicateResourceException("Flight Schedule Instance already exists for the given travel date.");
		}

		instance.setTravelDate(model.getTravelDate());

		flightScheduleInstanceRepository.save(instance);

		return true;
	}

	@Transactional
	public Boolean updateStatus(final Long id, final UpdateFlightScheduleInstanceStatusRequestModel model)
			throws InvalidInputException, ResourceNotFoundException {

		if (id == null || id <= NumericConstants.ZERO) {
			throw new InvalidInputException("Invalid Flight Schedule Instance Id.");
		}

		if (model == null || model.getStatus() == null) {
			throw new InvalidInputException("Status is required.");
		}

		FlightScheduleInstance instance = flightScheduleInstanceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Flight Schedule Instance not found."));

		instance.setStatus(model.getStatus());

		flightScheduleInstanceRepository.save(instance);

		return true;
	}

	@Transactional
	public Boolean updateFlightPrice(final UpdateFlightPriceRequestModel data) {

		if (data == null) {
			throw new InvalidInputException("Invalid Request Object.");
		}

		FlightSeatAvailability entry = flightSeatAvailabilityRepository
				.findByFlightScheduleInstanceIdAndSeatClassId(data.getFlightScheduleInstanceId(), data.getSeatTypeId())
				.orElseThrow(() -> new InvalidInputException("Flight Schedule not found."));

		entry.setPrice(data.getPrice());

		flightSeatAvailabilityRepository.save(entry);

		return true;
	}
}
