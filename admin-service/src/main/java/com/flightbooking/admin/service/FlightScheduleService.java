package com.flightbooking.admin.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightbooking.admin.dto.FlightScheduleRequest;
import com.flightbooking.admin.dto.FlightScheduleResponse;
import com.flightbooking.admin.entity.Airport;
import com.flightbooking.admin.entity.Flight;
import com.flightbooking.admin.entity.FlightSchedule;
import com.flightbooking.admin.exception.InvalidInputException;
import com.flightbooking.admin.exception.ResourceNotFoundException;
import com.flightbooking.admin.repository.AirportRepository;
import com.flightbooking.admin.repository.FlightRepository;
import com.flightbooking.admin.repository.FlightScheduleRepository;
import com.flightbooking.admin.util.CommonUtils;
import com.flightbooking.admin.util.NumericConstants;
import com.flightbooking.admin.util.StringConstants;

@Service
public class FlightScheduleService {

	@Autowired
	private FlightScheduleRepository flightScheduleRepository;

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private AirportRepository airportRepository;

	@Transactional
	public FlightScheduleResponse createNewFlightScheduleEntry(final FlightScheduleRequest model) {

		// Validate Incoming request object.
		validateFlightScheduleRequest(model);

		Map<String, Map<Long, String>> referenceMap = buildReferenceMap(model);

		FlightSchedule newSchedule = FlightSchedule.builder().flightId(model.getFlightId())
				.sourceAirportId(model.getSourceAirportId()).destinationAirportId(model.getDestinationAirportId())
				.departureTime(model.getDepartureTime()).arrivalTime(model.getArrivalTime())
				.effectiveFrom(model.getEffectiveFrom()).effectiveTo(model.getEffectiveTo())
				.arrivaleDayOffset(model.getArrivalDayOffset()).build();

		newSchedule = flightScheduleRepository.save(newSchedule);

		final FlightScheduleResponse response = mapObjToFlightScheduleModel(newSchedule, referenceMap);

		return response;
	}

	private FlightScheduleResponse mapObjToFlightScheduleModel(final FlightSchedule record,
			Map<String, Map<Long, String>> map) {

		FlightScheduleResponse model = new FlightScheduleResponse();

		if (CommonUtils.isValid(record)) {
			String flightNumber = map.get(StringConstants.FLIGHT_DETAILS).get(record.getFlightId());
			String sourceAirport = map.get(StringConstants.AIRPORT_DETAILS).get(record.getSourceAirportId());
			String destinationAirport = map.get(StringConstants.AIRPORT_DETAILS).get(record.getDestinationAirportId());

			model = FlightScheduleResponse.builder().flightScheduleId(record.getId()).flightNumber(flightNumber)
					.sourceAirportCode(sourceAirport).destinationAirportCode(destinationAirport)
					.departureTime(record.getDepartureTime()).arrivalTime(record.getArrivalTime())
					.effectiveFrom(record.getEffectiveFrom()).effectiveTo(record.getEffectiveTo())
					.arrivalDayOffset(record.getArrivaleDayOffset()).createdBy(record.getCreatedBy())
					.modifiedBy(record.getModifiedBy()).build();
		}

		return model;
	}

	@Transactional
	public FlightScheduleResponse updateFlightSchedule(final FlightScheduleRequest model) {

		if (model.getFlightScheduleId() == null || model.getFlightScheduleId() <= NumericConstants.ZERO) {
			throw new InvalidInputException("Invalid Flight Schedule Id.");
		}

		// Validate Incoming request object.
		validateFlightScheduleRequest(model);

		Map<String, Map<Long, String>> referenceMap = buildReferenceMap(model);

		FlightSchedule existingRecord = flightScheduleRepository.findById(model.getFlightScheduleId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Flight Schedule Id."));

		existingRecord.setFlightId(model.getFlightId());
		existingRecord.setSourceAirportId(model.getSourceAirportId());
		existingRecord.setDestinationAirportId(model.getDestinationAirportId());
		existingRecord.setDepartureTime(model.getDepartureTime());
		existingRecord.setArrivalTime(model.getArrivalTime());
		existingRecord.setEffectiveFrom(model.getEffectiveFrom());
		existingRecord.setEffectiveTo(model.getEffectiveTo());
		existingRecord.setArrivaleDayOffset(model.getArrivalDayOffset());

		existingRecord = flightScheduleRepository.save(existingRecord);

		final FlightScheduleResponse updatedSchedule = mapObjToFlightScheduleModel(existingRecord, referenceMap);

		return updatedSchedule;
	}

	private void validateFlightScheduleRequest(final FlightScheduleRequest model) {

		if (!CommonUtils.isValid(model)) {
			throw new InvalidInputException("Invalid flight schedule request object.");
		}

		if (model.getFlightId() == null || model.getFlightId() <= NumericConstants.ZERO) {
			throw new InvalidInputException("Invalid Flight Id.");
		}

		if (model.getSourceAirportId() == null || model.getSourceAirportId() <= NumericConstants.ZERO) {
			throw new InvalidInputException("Invalid source airport Id.");
		}

		if (model.getDestinationAirportId() == null || model.getDestinationAirportId() <= NumericConstants.ZERO) {
			throw new InvalidInputException("Invalid destination airport Id.");
		}

	}

	private Map<String, Map<Long, String>> buildReferenceMap(final FlightScheduleRequest model) {

		Flight flightDetails = flightRepository.findById(model.getFlightId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Flight Id."));

		Airport sourceAirport = airportRepository.findById(model.getSourceAirportId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid source airport."));

		Airport destinationAirport = airportRepository.findById(model.getDestinationAirportId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Destination airport."));

		if (sourceAirport.getCityId().equals(destinationAirport.getCityId())) {
			throw new InvalidInputException("Source and destination cities cannot be same.");
		}

		Map<Long, String> flightDetailsMap = new HashMap<>();
		flightDetailsMap.put(flightDetails.getId(), flightDetails.getFlightNumber());

		Map<Long, String> airportDetailsMap = new HashMap<>();
		airportDetailsMap.put(sourceAirport.getId(), sourceAirport.getAirportCode());
		airportDetailsMap.put(destinationAirport.getId(), destinationAirport.getAirportCode());

		Map<String, Map<Long, String>> referenceMap = new HashMap<>();
		referenceMap.put(StringConstants.FLIGHT_DETAILS, flightDetailsMap);
		referenceMap.put(StringConstants.AIRPORT_DETAILS, airportDetailsMap);

		return referenceMap;
	}
}
