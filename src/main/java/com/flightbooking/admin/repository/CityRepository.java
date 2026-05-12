package com.flightbooking.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightbooking.admin.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	boolean existsByCityCodeIgnoreCase(String cityCode);

	City findByCityCode(String cityCode);
	
	List<City> findByCountryId(Long countryId);
}
