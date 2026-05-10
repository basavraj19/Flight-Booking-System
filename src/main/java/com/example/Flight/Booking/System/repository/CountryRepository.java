package com.example.Flight.Booking.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Flight.Booking.System.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

	Country findBycountryCode(String countryCode);

	@Query("select c.id from Country c where c.id = :countryCode")
	Long getIdByCountryCode(@Param("countryCode") final String countryCode);
}
