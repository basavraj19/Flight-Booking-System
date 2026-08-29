package com.flightbooking.flight.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flightbooking.flight.entity.FlightSeatAvailability;

@Repository
public interface FlightSeatAvailabilityRepository extends JpaRepository<FlightSeatAvailability, Long> {

	Optional<FlightSeatAvailability> findByFlightScheduleInstanceIdAndSeatClassId(Long flightScheduleInstanceId,
			Long seatTypeId);

	List<FlightSeatAvailability> findByFlightScheduleInstanceIdIn(List<Long> flightScheduleInstanceIds);

	@Modifying
	@Query(nativeQuery = true, value = "UPDATE FLIGHT_SEAT_AVAILBAILITY SET BOOKED_SEATS = BOOKED_SEATS + :numberOfSeats"
			+ " WHERE FLIGHT_SCHEDULE_INSTANCE_ID = :flightScheduleInstanceId AND SEAT_CLASS_ID = :seatTypeId AND"
			+ " TOTAL_SETAS - BOOKED_SEAT >= :noOfSeats")
	int reserveSeats(@Param("flightScheduleInstanceId") Long flightScheduleInstanceId,
			@Param("seatTypeId") Long seatTypeId, @Param("numberOfSeats") int numberOfSeats);
}
