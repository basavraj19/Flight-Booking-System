package com.flightbooking.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.flightbooking.admin.dto.CityModel;
import com.flightbooking.admin.entity.City;
import com.flightbooking.admin.exception.DuplicateResourceException;
import com.flightbooking.admin.exception.InvalidInputException;
import com.flightbooking.admin.repository.CityRepository;
import com.flightbooking.admin.repository.CountryRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Transactional(propagation = Propagation.REQUIRED)
	public City createNewCityEntry(final CityModel city) throws Exception {

		if (!StringUtils.hasText(city.getCityName())) {
			throw new InvalidInputException("Invalid City name.");
		}

		if (!StringUtils.hasText(city.getCountryCode())) {
			throw new InvalidInputException("Invalid Country Code.");
		}

		boolean isRecordeExists = cityRepository.existsByCityNameIgnoreCase(city.getCityName());

		if (isRecordeExists) {
			throw new DuplicateResourceException("City Already Exists.");
		}

		Long countryId = countryRepository.getIdByCountryCode(city.getCountryCode());

		if (countryId == null) {
			throw new InvalidInputException("Invalid Country Code.");
		}

		City newCity = City.builder().cityName(city.getCityName()).countryId(countryId).createdBy(city.getCreatedBy())
				.modifiedBy(city.getModifiedBy()).build();

		newCity = cityRepository.save(newCity);

		return newCity;
	}

}
