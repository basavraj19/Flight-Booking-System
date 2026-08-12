package com.flightbooking.flight.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightbooking.flight.entity.FlightSeatConfiguration;

@Repository
public interface FlightSeatConfigurationRepository extends JpaRepository<FlightSeatConfiguration, Long>{

   List<FlightSeatConfiguration> findByFlightId(Long flightId);
   
   boolean existsByFlightIdAndSeatClassId(Long flightId, Long seatClassId);
}
