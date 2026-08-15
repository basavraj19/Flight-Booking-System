package com.flightbooking.flight.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flightbooking.flight.entity.FlightScheduleInstance;

@Repository
public interface FlightScheduleInstanceRepository extends JpaRepository<FlightScheduleInstance, Long> {

	boolean existsByFlightScheduleIdAndTravelDate(Long flightScheduleId, LocalDate travelDate);

	boolean existsByFlightScheduleIdAndTravelDateAndIdNot(Long flightScheduleId, LocalDate travelDate, Long id);

	@Query(nativeQuery = true, value = """
			SELECT ID,
			       FLIGHT_SCHEDULE_ID,
			       TRAVEL_DATE,
			       STATUS
			FROM FLIGHT_SCHEDULE_INSTANCE
			WHERE FLIGHT_SCHEDULE_ID IN (:flightScheduleIds)
			  AND TRAVEL_DATE BETWEEN :startDate AND :endDate
			  AND STATUS IN ('SCHEDULED', 'DELAYED')
			""")
	public List<FlightScheduleInstance> getFlightSchedulesInstances(
			@Param("flightScheduleIds") final List<Long> flightScheduleIds,
			@Param("startDate") final LocalDate startDate, @Param("endDate") final LocalDate endDate);

}
