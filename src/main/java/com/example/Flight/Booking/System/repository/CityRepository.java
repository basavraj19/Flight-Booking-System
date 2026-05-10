package com.example.Flight.Booking.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Flight.Booking.System.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer>{

	boolean existsByCityNameIgnoreCase(String cityName);
}
