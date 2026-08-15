package com.flightbooking.flight.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightbooking.flight.entity.FlightSeatAvailability;

@Repository
public interface FlightSeatAvailabilityRepository extends JpaRepository<FlightSeatAvailability, Long> {

	Optional<FlightSeatAvailability> findByFlightScheduleInstanceIdAndSeatClassId(Long flightScheduleInstanceId,
			Long seatTypeId);

	List<FlightSeatAvailability> findByFlightScheduleInstanceIdIn(List<Long> flightScheduleInstanceIds);
}
