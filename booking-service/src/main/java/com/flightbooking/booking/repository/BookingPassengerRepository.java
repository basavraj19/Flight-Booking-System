package com.flightbooking.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightbooking.booking.entity.BookingPassenger;

@Repository
public interface BookingPassengerRepository extends JpaRepository<BookingPassenger, Long> {

	List<BookingPassenger> findPasssengerDetailsByBookingId(final Long bookingId);
}
