package com.flightbooking.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.flightbooking.flight.entity.FlightSchedule;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Long> {

	@Query(nativeQuery = true, value = "SELECT FLIGHT_ID FROM FLIGHT_SCHEDULE WHERE ID = :flightScheduleId")
	public Long getFlightIdByFlightScheduleId(@Param("flightScheduleId") final Long flightScheduleId);
}
