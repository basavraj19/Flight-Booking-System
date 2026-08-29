package com.flightbooking.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flightbooking.booking.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	@Modifying
	@Query(nativeQuery = true, value = "UPDATE BOOKING SET BOOKING_STATUS = :bookingStatus, PAYMENT_STATUS = :paymentStatus"
			+ " WHERE ID = :bookingId")
	int updateBookingStatus(@Param("bookingStatus") String bookingStatus, @Param("paymentStatus") String paymentStatus,
			@Param("bookingId") Long bookingId);
}
