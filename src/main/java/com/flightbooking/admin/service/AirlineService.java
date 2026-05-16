package com.flightbooking.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.flightbooking.admin.dto.AirlineModel;
import com.flightbooking.admin.entity.Airline;
import com.flightbooking.admin.exception.DuplicateResourceException;
import com.flightbooking.admin.exception.InvalidInputException;
import com.flightbooking.admin.repository.AirlineRepository;

@Service
public class AirlineService {

	@Autowired
	private AirlineRepository airlineRepository;

	@Transactional
	public Airline createNewEntry(final AirlineModel model) {

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

		return airline;
	}
}
