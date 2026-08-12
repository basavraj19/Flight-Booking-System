package com.flightbooking.flight.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.flightbooking.flight.entity.FlightSeatInventory;

@Repository
public interface FlightSeatInventoryRepository extends JpaRepository<FlightSeatInventory, Long> {

	boolean existsByFlightScheduleIdAndTravelDate(Long flightScheduleId, LocalDate travelDate);

	Optional<FlightSeatInventory> findByFlightScheduleIdSeatClassIdAndTravelDate(Long flightScheduleId, Long seatTypeId,
			LocalDate travelDate);
}
