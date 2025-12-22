package com.example.Flight.Booking.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Flight.Booking.System.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

	Country findBycountryCode(String countryCode);
}
