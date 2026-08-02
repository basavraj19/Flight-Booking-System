package com.flightbooking.flight.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightbooking.flight.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

	boolean existsByFlightNumberIgnoreCase(String flightNumber);

	Flight findByFlightNumber(String flightNumber);

	List<Flight> findByAirlineId(Long airlineId);
}
