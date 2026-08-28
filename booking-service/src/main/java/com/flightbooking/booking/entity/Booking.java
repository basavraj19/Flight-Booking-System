package com.flightbooking.booking.entity;

import java.math.BigDecimal;

import com.flightbooking.booking.util.BookingStatus;
import com.flightbooking.booking.util.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking extends BaseEntity {

	@Column(name = "booking_reference", unique = true, length = 20)
	@NotNull
	private String bookingReference;

	@Column(name = "user_id")
	@NotNull
	private Long userId;

	@Column(name = "flight_schedule_instance_id")
	@NotNull
	private Long flightScheduleInstanceId;

	@Column(name = "seat_class_id")
	@NotNull
	private Long seatClassId;

	@Column(name = "no_of_seats")
	@Positive
	private int noOfSeats;

	@Column(name = "price_per_seat", nullable = false, precision = 10, scale = 2)
	@NotNull
	private BigDecimal pricePerSeat;

	@Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
	@NotNull
	private BigDecimal totalAmount;

	@Enumerated(EnumType.STRING)
	@Column(name = "booking_status", length = 30)
	@NotNull
	private BookingStatus bookingStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status", length = 30)
	@NotNull
	private PaymentStatus paymentStatus;
}