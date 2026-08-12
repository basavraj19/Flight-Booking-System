package com.flightbooking.flight.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightbooking.flight.dto.AirportResponseModel;
import com.flightbooking.flight.dto.FlightScheduleRequest;
import com.flightbooking.flight.dto.FlightScheduleResponse;
import com.flightbooking.flight.entity.Flight;
import com.flightbooking.flight.entity.FlightSchedule;
import com.flightbooking.flight.exception.InvalidInputException;
import com.flightbooking.flight.exception.ResourceNotFoundException;
import com.flightbooking.flight.repository.FlightRepository;
import com.flightbooking.flight.repository.FlightScheduleRepository;
import com.flightbooking.flight.util.CommonUtils;
import com.flightbooking.flight.util.NumericConstants;
import com.flightbooking.flight.util.StringConstants;
import com.flightbooking.flight.client.AdminService;

@Service
public class FlightScheduleService {

	@Autowired
	private FlightScheduleRepository flightScheduleRepository;

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private AdminService adminService;

	@Transactional
	public FlightScheduleResponse createNewFlightScheduleEntry(final FlightScheduleRequest model) {

		// Validate Incoming request object.
		validateFlightScheduleRequest(model);

		Map<String, Map<Long, String>> referenceMap = buildReferenceMap(model);

		FlightSchedule newSchedule = FlightSchedule.builder().flightId(model.getFlightId())
				.sourceAirportId(model.getSourceAirportId()).destinationAirportId(model.getDestinationAirportId())
				.departureTime(model.getDepartureTime()).arrivalTime(model.getArrivalTime())
				.effectiveFrom(model.getEffectiveFrom()).effectiveTo(model.getEffectiveTo())
				.arrivalDayOffset(model.getArrivalDayOffset()).build();

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
					.arrivalDayOffset(record.getArrivalDayOffset()).createdBy(record.getCreatedBy())
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
		existingRecord.setArrivalDayOffset(model.getArrivalDayOffset());

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

		AirportResponseModel sourceAirport = adminService.getAirportDetailsById(model.getSourceAirportId()).getResult();

		AirportResponseModel destinationAirport = adminService.getAirportDetailsById(model.getDestinationAirportId())
				.getResult();

		if (sourceAirport.getCityId().equals(destinationAirport.getCityId())) {
			throw new InvalidInputException("Source and destination cities cannot be same.");
		}

		Map<Long, String> flightDetailsMap = new HashMap<>();
		flightDetailsMap.put(flightDetails.getId(), flightDetails.getFlightNumber());

		Map<Long, String> airportDetailsMap = new HashMap<>();
		airportDetailsMap.put(sourceAirport.getRecordId(), sourceAirport.getAirportCode());
		airportDetailsMap.put(destinationAirport.getRecordId(), destinationAirport.getAirportCode());

		Map<String, Map<Long, String>> referenceMap = new HashMap<>();
		referenceMap.put(StringConstants.FLIGHT_DETAILS, flightDetailsMap);
		referenceMap.put(StringConstants.AIRPORT_DETAILS, airportDetailsMap);

		return referenceMap;
	}
}
