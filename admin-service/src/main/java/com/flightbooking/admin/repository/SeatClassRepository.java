package com.flightbooking.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightbooking.admin.entity.SeatClass;

@Repository
public interface SeatClassRepository extends JpaRepository<SeatClass, Long> {

}
