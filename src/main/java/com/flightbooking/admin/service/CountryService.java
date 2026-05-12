package com.flightbooking.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.flightbooking.admin.dto.CountryModel;
import com.flightbooking.admin.entity.Country;
import com.flightbooking.admin.exception.DuplicateResourceException;
import com.flightbooking.admin.exception.InvalidInputException;
import com.flightbooking.admin.exception.ResourceNotFoundException;
import com.flightbooking.admin.repository.CountryRepository;

@Service
public class CountryService {

	@Autowired
	private CountryRepository countryRepository;

	@Transactional
	public Country saveNewCountryDetails(final CountryModel record) {

		String countryCode = record.getCountryCode().trim().toUpperCase();

		if (!StringUtils.hasText(countryCode) || countryCode.length() < 2) {
			throw new InvalidInputException("Invalid Country Code.");
		}

		if (!StringUtils.hasText(record.getCountryName())) {
			throw new InvalidInputException("Invalid Country Name.");
		}

		Country existingRecord = countryRepository.findBycountryCode(countryCode);

		if (existingRecord != null) {
			throw new DuplicateResourceException("Country Already Exists.");
		}

		Country newCountry = Country.builder().countryCode(countryCode).countryName(record.getCountryName())
				.createdBy(record.getCreatedBy()).modifiedBy(record.getModifiedBy()).build();

		newCountry = countryRepository.save(newCountry);

		return newCountry;
	}

	public Country getCountryDetailsByCountryCode(final String countryCode) {
		String validCountryCode = countryCode.trim().toUpperCase();

		if (!StringUtils.hasText(validCountryCode) || validCountryCode.length() < 2) {
			throw new InvalidInputException("Invalid Country Code.");
		}

		Country record = countryRepository.findBycountryCode(validCountryCode);

		if (record == null) {
			throw new ResourceNotFoundException("Country with code " + validCountryCode + " does not exists.");
		}

		return record;
	}

	public List<Country> getAllCountryDetails() {

		List<Country> record = countryRepository.findAll();

		if (record.isEmpty()) {
			throw new ResourceNotFoundException("No Data found.");
		}

		return record;
	}

	@Transactional
	public Country updateDetails(final CountryModel record) {

		String countryCode = record.getCountryCode().trim().toUpperCase();

		if (record.getRecordId() <= 0) {
			throw new InvalidInputException("Invalid Id.");
		}

		if (!StringUtils.hasText(countryCode) || countryCode.length() < 2) {
			throw new InvalidInputException("Invalid Country Code.");
		}

		if (!StringUtils.hasText(record.getCountryName())) {
			throw new InvalidInputException("Invalid Country Name.");
		}

		Country existingRecord = countryRepository.findById(record.getRecordId())
				.orElseThrow(() -> new ResourceNotFoundException("Country does not exists."));

		existingRecord.setCountryCode(countryCode);
		existingRecord.setCountryName(record.getCountryName());

		Country updateRecord = countryRepository.save(existingRecord);

		return updateRecord;
	}
}
