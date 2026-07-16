package com.flightbooking.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.flightbooking.admin.dto.AirlineRequestModel;
import com.flightbooking.admin.dto.AirlineResponseModel;
import com.flightbooking.admin.entity.Airline;
import com.flightbooking.admin.exception.DuplicateResourceException;
import com.flightbooking.admin.exception.InvalidInputException;
import com.flightbooking.admin.exception.ResourceNotFoundException;
import com.flightbooking.admin.repository.AirlineRepository;
import com.flightbooking.admin.util.CommonUtils;

@Service
public class AirlineService {

	@Autowired
	private AirlineRepository airlineRepository;

	@Transactional
	public AirlineResponseModel createNewEntry(final AirlineRequestModel model) {

		if (!StringUtils.hasText(model.getAirlineName())) {
			throw new InvalidInputException("Invalid Airline name.");
		}

		boolean isRecordExists = airlineRepository.existsByAirlineNameIgnoreCase(model.getAirlineName().trim());

		if (isRecordExists) {
			throw new DuplicateResourceException("Airport Already Exists.");
		}

		Airline airline = Airline.builder().airlineName(model.getAirlineName().trim()).build();

		airline = airlineRepository.save(airline);

		final AirlineResponseModel newRecord = mapObjToAirlineModel(airline);

		return newRecord;
	}

	private AirlineResponseModel mapObjToAirlineModel(final Airline airline) {

		final AirlineResponseModel model = new AirlineResponseModel();

		if (CommonUtils.isValid(airline)) {
			model.setRecordId(airline.getId());
			model.setAirlineName(airline.getAirlineName());
			model.setCreateBy(airline.getCreatedBy());
			model.setModifiedBy(airline.getModifiedBy());
		}
		return model;
	}

	@Transactional(readOnly = true)
	public List<AirlineResponseModel> getAllAirlineEntries() {
		final List<Airline> airlineList = airlineRepository.findAll();

		if (!CommonUtils.isValid(airlineList)) {
			throw new ResourceNotFoundException("No Records found.");
		}

		List<AirlineResponseModel> resultSet = airlineList.stream().map(this::mapObjToAirlineModel).toList();

		return resultSet;
	}
}
