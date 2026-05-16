package com.flightbooking.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightbooking.admin.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

	boolean existsByFlightNumberIgnoreCase(String flightNumber);

	Flight findByFlightNumber(String flightNumber);

	List<Flight> findByAirlineId(int airlineId);
}
