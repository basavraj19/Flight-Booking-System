package com.flightbooking.flight.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import org.springframework.data.annotation.CreatedBy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FlightSeatInventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Column(name = "flight_schedule_id", nullable = false)
	private Long FlightScheduleId;

	@Column(name = "seat_class_id", nullable = false)
	private Long seatClassId;

	@Column(name = "travel_date")
	@NotNull
	private LocalDate travelDate;

	@Column(name = "total_seats", nullable = false)
	private int totalSeats;

	@Column(name = "booked_seats", nullable = false)
	private int bookedSeats;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(name = "created_by", updatable = false)
	@CreatedBy
	private String createdBy;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;
}
