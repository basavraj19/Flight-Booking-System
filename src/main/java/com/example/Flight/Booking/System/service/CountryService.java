package com.example.Flight.Booking.System.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Flight.Booking.System.dto.CountryRequestDto;
import com.example.Flight.Booking.System.entity.Country;
import com.example.Flight.Booking.System.exception.DuplicateResourceException;
import com.example.Flight.Booking.System.exception.InvalidInputException;
import com.example.Flight.Booking.System.repository.CountryRepository;

@Service
public class CountryService {

	@Autowired
	private CountryRepository countryRepository;

	public Country saveNewCountryDetails(final CountryRequestDto record) throws Exception {

		if (record.getCountryCode() == null || record.getCountryCode().isEmpty() || record.getCountryName() == null
				|| record.getCountryName().isEmpty()) {
			throw new InvalidInputException("Invalid input value.");
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
}
