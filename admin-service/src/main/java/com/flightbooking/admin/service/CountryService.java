package com.flightbooking.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.flightbooking.admin.dto.CountryRequestModel;
import com.flightbooking.admin.dto.CountryResponseModel;
import com.flightbooking.admin.entity.Country;
import com.flightbooking.admin.exception.DuplicateResourceException;
import com.flightbooking.admin.exception.InvalidInputException;
import com.flightbooking.admin.exception.ResourceNotFoundException;
import com.flightbooking.admin.repository.CountryRepository;
import com.flightbooking.admin.util.CommonUtils;
import com.flightbooking.admin.util.NumericConstants;

@Service
public class CountryService {

	@Autowired
	private CountryRepository countryRepository;

	@Transactional
	public CountryResponseModel saveNewCountryDetails(final CountryRequestModel record) {

		String countryCode = record.getCountryCode().trim().toUpperCase();

		if (!StringUtils.hasText(countryCode)
				|| !(countryCode.length() >= NumericConstants.TWO && countryCode.length() <= NumericConstants.THREE)) {
			throw new InvalidInputException("Invalid Country Code.");
		}

		if (!StringUtils.hasText(record.getCountryName())) {
			throw new InvalidInputException("Invalid Country Name.");
		}

		Country existingRecord = countryRepository.findBycountryCode(countryCode);

		if (existingRecord != null) {
			throw new DuplicateResourceException("Country Already Exists.");
		}

		Country newCountry = Country.builder().countryCode(countryCode).countryName(record.getCountryName()).build();

		newCountry = countryRepository.save(newCountry);

		CountryResponseModel model = mapFieldsToCountryResponseModel(newCountry);

		return model;
	}

	public CountryResponseModel getCountryDetailsByCountryCode(final String countryCode) {
		String validCountryCode = countryCode.trim().toUpperCase();

		if (!StringUtils.hasText(validCountryCode) || !(validCountryCode.length() >= NumericConstants.TWO
				&& validCountryCode.length() <= NumericConstants.THREE)) {
			throw new InvalidInputException("Invalid Country Code.");
		}

		Country record = countryRepository.findBycountryCode(validCountryCode);

		if (record == null) {
			throw new ResourceNotFoundException("Country with code " + validCountryCode + " does not exists.");
		}

		CountryResponseModel model = mapFieldsToCountryResponseModel(record);

		return model;
	}

	public List<CountryResponseModel> getAllCountryDetails() {

		List<Country> recordList = countryRepository.findAll();

		if (recordList.isEmpty()) {
			throw new ResourceNotFoundException("No Data found.");
		}

		List<CountryResponseModel> resultSet = new ArrayList<>();

		for (Country country : recordList) {
			CountryResponseModel model = mapFieldsToCountryResponseModel(country);
			resultSet.add(model);
		}

		return resultSet;
	}

	@Transactional
	public CountryResponseModel updateDetails(final CountryRequestModel record) {

		String countryCode = record.getCountryCode().trim().toUpperCase();

		if (record.getRecordId() == null || record.getRecordId() <= NumericConstants.ZERO) {
			throw new InvalidInputException("Invalid Id.");
		}

		if (!StringUtils.hasText(countryCode)
				|| !(countryCode.length() >= NumericConstants.TWO && countryCode.length() <= NumericConstants.THREE)) {
			throw new InvalidInputException("Invalid Country Code.");
		}

		if (!StringUtils.hasText(record.getCountryName())) {
			throw new InvalidInputException("Invalid Country Name.");
		}

		Country existingRecord = countryRepository.findById(record.getRecordId())
				.orElseThrow(() -> new ResourceNotFoundException("Country does not exists."));

		existingRecord.setCountryCode(countryCode);
		existingRecord.setCountryName(record.getCountryName());

		countryRepository.save(existingRecord);

		CountryResponseModel updateRecord = mapFieldsToCountryResponseModel(existingRecord);

		return updateRecord;
	}

	private CountryResponseModel mapFieldsToCountryResponseModel(final Country record) {

		CountryResponseModel model = new CountryResponseModel();

		if (CommonUtils.isValid(model)) {
			model = CountryResponseModel.builder().recordId(record.getId()).countryCode(record.getCountryCode())
					.countryName(record.getCountryName()).createdBy(record.getCreatedBy())
					.modifiedBy(record.getModifiedBy()).build();

		}
		return model;
	}
}
