package com.flightbooking.flight.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightbooking.flight.entity.FlightScheduleInstance;

@Repository
public interface FlightScheduleInstanceRepository extends JpaRepository<FlightScheduleInstance, Long> {

	boolean existsByFlightScheduleIdAndTravelDate(Long flightScheduleId, LocalDate travelDate);

	boolean existsByFlightScheduleIdAndTravelDateAndIdNot(Long flightScheduleId, LocalDate travelDate, Long id);
}
