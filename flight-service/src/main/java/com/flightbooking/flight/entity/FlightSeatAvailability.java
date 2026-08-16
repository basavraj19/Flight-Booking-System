package com.flightbooking.flight.entity;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@EntityListeners(AuditingEntityListener.class)
public class FlightSeatAvailability {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Column(name = "flight_schedule_instance_id", nullable = false)
	private Long flightScheduleInstanceId;

	@Column(name = "seat_class_id", nullable = false)
	private Long seatClassId;

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
	@CreatedDate
	private Instant createdAt;
}
