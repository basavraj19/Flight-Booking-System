package com.flightbooking.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

	@Transactional(propagation = Propagation.REQUIRED)
	public Country saveNewCountryDetails(final CountryModel record) throws Exception {

		if (!StringUtils.hasText(record.getCountryCode()) || record.getCountryCode().length() < 2) {
			throw new InvalidInputException("Invalid Country Code.");
		}

		if (!StringUtils.hasText(record.getCountryName())) {
			throw new InvalidInputException("Invalid Country Name.");
		}

		Country existingRecord = countryRepository.findBycountryCode(record.getCountryCode());

		if (existingRecord != null) {
			throw new DuplicateResourceException("Country Already Exists.");
		}

		Country newCountry = Country.builder().countryCode(record.getCountryCode()).countryName(record.getCountryName())
				.createdBy(record.getCreatedBy()).modifiedBy(record.getModifiedBy()).build();

		try {
			newCountry = countryRepository.save(newCountry);
		} catch (Exception e) {
			throw new Exception(e);
		}

		return newCountry;
	}

	public Country getCountryDetailsByCountryCode(final String countryCode) throws Exception {
		if (!StringUtils.hasText(countryCode) || countryCode.length() < 2) {
			throw new InvalidInputException("Invalid Country Code.");
		}

		Country record = countryRepository.findBycountryCode(countryCode);

		if (record == null) {
			throw new ResourceNotFoundException("Country with code " + countryCode + " doesn't exists.");
		}

		return record;
	}

	public Country updateDetails(final CountryModel record) throws Exception {

		if (record.getRecordId() <= 0) {
			throw new InvalidInputException("Invalid Id.");
		}

		if (!StringUtils.hasText(record.getCountryCode()) || record.getCountryCode().length() < 2) {
			throw new InvalidInputException("Invalid Country Code.");
		}

		if (!StringUtils.hasText(record.getCountryName())) {
			throw new InvalidInputException("Invalid Country Name.");
		}

		Country existingRecord = countryRepository.findById(record.getRecordId())
				.orElseThrow(() -> new ResourceNotFoundException("Country doesn't exists."));

		existingRecord.setCountryCode(record.getCountryCode());
		existingRecord.setCountryName(record.getCountryName());

		Country updateRecord = countryRepository.save(existingRecord);

		return updateRecord;
	}
}
