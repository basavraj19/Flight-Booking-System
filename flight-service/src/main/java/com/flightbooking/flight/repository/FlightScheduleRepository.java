package com.flightbooking.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightbooking.flight.entity.FlightSchedule;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Long>{

}
