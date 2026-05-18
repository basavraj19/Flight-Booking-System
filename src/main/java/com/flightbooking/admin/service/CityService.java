package com.flightbooking.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.flightbooking.admin.dto.CityRequestModel;
import com.flightbooking.admin.dto.CityResponseModel;
import com.flightbooking.admin.dto.CountryResponseModel;
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
	public CityResponseModel createNewCityEntry(final CityRequestModel city) {

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
				.build();

		newCity = cityRepository.save(newCity);

		CityResponseModel newRecord = CityResponseModel.builder().recordId(newCity.getId())
				.cityCode(newCity.getCityCode()).cityName(newCity.getCityName()).countryCode(countryCode)
				.createdBy(newCity.getCreatedBy()).modifiedBy(newCity.getModifiedBy()).build();

		return newRecord;
	}

	@Transactional(readOnly = true)
	public CityResponseModel getCityDetailsByCode(final String cityCode) {
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

		CityResponseModel data = CityResponseModel.builder().recordId(result.getId()).cityCode(result.getCityCode())
				.cityName(result.getCityName()).countryCode(country.getCountryCode()).createdBy(result.getCreatedBy())
				.modifiedBy(result.getModifiedBy()).build();

		return data;
	}

	@Transactional(readOnly = true)
	public List<CityResponseModel> getCityDetailsByCountry(final String countryCode) {

		CountryResponseModel countryDetails = countryService.getCountryDetailsByCountryCode(countryCode);

		List<CityResponseModel> result = new ArrayList<>();

		if (CommonUtils.isValid(countryDetails)) {
			List<City> dataList = cityRepository.findByCountryId(countryDetails.getRecordId());

			if (CommonUtils.isValid(dataList)) {

				for (City record : dataList) {

					CityResponseModel model = CityResponseModel.builder().recordId(record.getId())
							.cityCode(record.getCityCode()).cityName(record.getCityName())
							.countryCode(countryDetails.getCountryCode()).createdBy(record.getCreatedBy())
							.modifiedBy(record.getModifiedBy()).build();

					result.add(model);
				}
			}
		}

		return result;
	}

	@Transactional
	public CityResponseModel updateCityDetails(final CityRequestModel model) {

		String cityCode = model.getCityCode().trim().toUpperCase();
		String countryCode = model.getCountryCode().trim().toUpperCase();

		if (model.getRecordId() == null || model.getRecordId() <= NumericConstants.ZERO) {
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

		CityResponseModel updatedRecord = CityResponseModel.builder().recordId(result.getId())
				.cityCode(result.getCityCode()).cityName(result.getCityName()).countryCode(countryCode)
				.createdBy(result.getCreatedBy()).modifiedBy(result.getModifiedBy()).build();

		return updatedRecord;
	}
}
