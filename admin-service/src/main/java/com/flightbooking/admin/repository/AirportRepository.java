package com.flightbooking.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightbooking.admin.entity.Airport;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

	boolean existsByAirportCode(String airportCode);

	List<Airport> findAllByCityId(Long cityId);
}
