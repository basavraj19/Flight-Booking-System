package com.flightbooking.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.flightbooking.admin.dto.AirlineModel;
import com.flightbooking.admin.entity.Airline;
import com.flightbooking.admin.exception.DuplicateResourceException;
import com.flightbooking.admin.exception.InvalidInputException;
import com.flightbooking.admin.repository.AirlineRepository;
import com.flightbooking.admin.util.CommonUtils;

@Service
public class AirlineService {

	@Autowired
	private AirlineRepository airlineRepository;

	@Transactional
	public AirlineModel createNewEntry(final AirlineModel model) {

		if (!StringUtils.hasText(model.getAirlineName())) {
			throw new InvalidInputException("Invalid Airline name.");
		}

		boolean isRecordExists = airlineRepository.existsByAirlineNameIgnoreCase(model.getAirlineName().trim());

		if (isRecordExists) {
			throw new DuplicateResourceException("Airport Already Exists.");
		}

		Airline airline = Airline.builder().airlineName(model.getAirlineName().trim()).createdBy(model.getCreateBy())
				.modifiedBy(model.getModifiedBy()).build();

		airline = airlineRepository.save(airline);

		final AirlineModel newRecord = mapObjToAirlineModel(airline);

		return newRecord;
	}

	private AirlineModel mapObjToAirlineModel(final Airline airline) {

		final AirlineModel model = new AirlineModel();

		if (CommonUtils.isValid(airline)) {
			model.setRecordId(airline.getId());
			model.setAirlineName(airline.getAirlineName());
			model.setCreateBy(airline.getCreatedBy());
			model.setModifiedBy(airline.getModifiedBy());
		}
		return model;
	}

	public List<AirlineModel> getAllAirlineEntries() {
		final List<Airline> airlineList = airlineRepository.findAll();

		final List<AirlineModel> resultSet = new ArrayList<>();

		if (CommonUtils.isValid(airlineList)) {

			for (Airline record : airlineList) {
				final AirlineModel newEntry = mapObjToAirlineModel(record);
				resultSet.add(newEntry);
			}
		}

		return resultSet;
	}
}
