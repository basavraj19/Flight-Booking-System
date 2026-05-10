package com.flightbooking.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightbooking.admin.entity.Airline;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Integer> {

}
