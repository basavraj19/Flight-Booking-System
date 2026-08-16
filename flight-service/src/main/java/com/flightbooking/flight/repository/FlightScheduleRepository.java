package com.flightbooking.flight.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flightbooking.flight.entity.FlightSchedule;

@Repository
public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Long> {

	@Query(nativeQuery = true, value = "SELECT FLIGHT_ID FROM FLIGHT_SCHEDULE WHERE ID = :flightScheduleId")
	public Long getFlightIdByFlightScheduleId(@Param("flightScheduleId") final Long flightScheduleId);

	@Query(nativeQuery = true, value = """
			  SELECT ID,
               CREATED_BY,
               CREATED_AT,
               MODIFIED_BY,
               MODIFIED_AT,
               FLIGHT_ID,
               SOURCE_AIRPORT_ID,
               DESTINATION_AIRPORT_ID,
               DEPARTURE_TIME,
               ARRIVAL_TIME,
               EFFECTIVE_FROM,
               EFFECTIVE_TO,
               ARRIVAL_DAY_OFFSET
			FROM FLIGHT_SCHEDULE
			WHERE SOURCE_AIRPORT_ID IN (:sourceAirportIds)
			  AND DESTINATION_AIRPORT_ID IN (:destinationAirportIds)
			  AND EFFECTIVE_FROM <= :endDate
			  AND EFFECTIVE_TO >= :startDate
			""")
	public List<FlightSchedule> getFlightSchedules(@Param("sourceAirportIds") final List<Long> sourceAirportIds,
			@Param("destinationAirportIds") final List<Long> destinationAirportIds,
			@Param("startDate") final LocalDate startDate, @Param("endDate") final LocalDate endDate);
}
