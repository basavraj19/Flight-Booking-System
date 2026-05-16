package com.flightbooking.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.flightbooking.admin.dto.CityModel;
import com.flightbooking.admin.entity.City;
import com.flightbooking.admin.entity.Country;
import com.flightbooking.admin.exception.DuplicateResourceException;
import com.flightbooking.admin.exception.InvalidInputException;
import com.flightbooking.admin.exception.ResourceNotFoundException;
import com.flightbooking.admin.repository.CityRepository;
import com.flightbooking.admin.repository.CountryRepository;
import com.flightbooking.admin.util.CommonUtils;
import com.flightbooking.admin.util.NumericConstants;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private CountryService countryService;

	@Transactional
	public City createNewCityEntry(final CityModel city) {

		String countryCode = city.getCountryCode().trim().toUpperCase();
		String cityCode = city.getCityCode().trim().toUpperCase();

		if (!StringUtils.hasText(cityCode)
				|| !(cityCode.length() >= NumericConstants.TWO && cityCode.length() <= NumericConstants.THREE)) {
			throw new InvalidInputException("Invalid City Code.");
		}

		if (!StringUtils.hasText(city.getCityName())) {
			throw new InvalidInputException("Invalid City name.");
		}

		if (!StringUtils.hasText(countryCode)
				|| !(countryCode.length() >= NumericConstants.TWO && countryCode.length() <= NumericConstants.THREE)) {
			throw new InvalidInputException("Invalid Country Code.");
		}

		boolean isRecordeExists = cityRepository.existsByCityCodeIgnoreCase(cityCode);

		if (isRecordeExists) {
			throw new DuplicateResourceException("City Already Exists.");
		}

		Long countryId = countryRepository.getIdByCountryCode(countryCode);

		if (countryId == null) {
			throw new InvalidInputException("Invalid Country Code.");
		}

		City newCity = City.builder().cityCode(cityCode).cityName(city.getCityName().trim()).countryId(countryId)
				.createdBy(city.getCreatedBy()).modifiedBy(city.getModifiedBy()).build();

		newCity = cityRepository.save(newCity);

		return newCity;
	}

	public CityModel getCityDetailsByCode(final String cityCode) {
		String validCityCode = cityCode.trim().toUpperCase();

		if (!StringUtils.hasText(validCityCode) || !(validCityCode.length() >= NumericConstants.TWO
				&& validCityCode.length() <= NumericConstants.THREE)) {
			throw new InvalidInputException("Invalid City Code.");
		}

		City result = cityRepository.findByCityCode(validCityCode);

		if (result == null) {
			throw new ResourceNotFoundException("City with code " + validCityCode + " does not exists.");
		}

		Country country = countryRepository.findById(result.getCountryId())
				.orElseThrow(() -> new ResourceNotFoundException("Country does not exists."));

		CityModel data = CityModel.builder().recordId(result.getId()).cityCode(result.getCityCode())
				.cityName(result.getCityName()).countryCode(country.getCountryCode()).createdBy(result.getCreatedBy())
				.modifiedBy(result.getModifiedBy()).build();

		return data;
	}

	@Transactional
	public List<CityModel> getCityDetailsByCountry(final String countryCode) {

		Country countryDetails = countryService.getCountryDetailsByCountryCode(countryCode);

		List<CityModel> result = new ArrayList<>();

		if (CommonUtils.isValid(countryDetails)) {
			List<City> dataList = cityRepository.findByCountryId(countryDetails.getId());

			if (CommonUtils.isValid(dataList)) {

				for (City record : dataList) {

					CityModel model = CityModel.builder().recordId(record.getId()).cityCode(record.getCityCode())
							.cityName(record.getCityName()).countryCode(countryDetails.getCountryCode())
							.createdBy(record.getCreatedBy()).modifiedBy(record.getModifiedBy()).build();

					result.add(model);
				}
			}
		}

		return result;
	}

	@Transactional
	public CityModel updateCityDetails(final CityModel model) {

		String cityCode = model.getCityCode().trim().toUpperCase();
		String countryCode = model.getCountryCode().trim().toUpperCase();

		if (model.getRecordId() <= 0) {
			throw new InvalidInputException("Invalid Id.");
		}

		if (!StringUtils.hasText(cityCode)
				|| !(cityCode.length() >= NumericConstants.TWO && cityCode.length() <= NumericConstants.THREE)) {
			throw new InvalidInputException("Invalid City Code.");
		}

		if (!StringUtils.hasText(model.getCityName())) {
			throw new InvalidInputException("Invalid City name.");
		}

		if (!StringUtils.hasText(countryCode)
				|| !(countryCode.length() >= NumericConstants.TWO && countryCode.length() <= NumericConstants.THREE)) {
			throw new InvalidInputException("Invalid Country Code.");
		}

		City result = cityRepository.findById(model.getRecordId())
				.orElseThrow(() -> new ResourceNotFoundException("City not exists."));

		Long countryId = countryRepository.getIdByCountryCode(countryCode);

		if (countryId == null) {
			throw new InvalidInputException("Invalid Country Code.");
		}

		result.setCityCode(cityCode);
		result.setCityName(model.getCityName().trim());
		result.setCountryId(countryId);

		result = cityRepository.save(result);

		CityModel updatedRecord = CityModel.builder().recordId(result.getId()).cityCode(result.getCityCode())
				.cityName(result.getCityName()).countryCode(countryCode).createdBy(result.getCreatedBy())
				.modifiedBy(result.getModifiedBy()).build();

		return updatedRecord;
	}
}
