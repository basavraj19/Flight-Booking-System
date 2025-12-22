package com.example.Flight.Booking.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Flight.Booking.System.entity.Airline;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Integer> {

}
