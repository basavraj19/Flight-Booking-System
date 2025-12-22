package com.example.Flight.Booking.System.entity;

import java.time.Instant;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class FlightSchedule extends BaseEntity {

	@Column(name = "flight_id", nullable = false)
	private Integer flightId;

	@Column(name = "source_airport_id", nullable = false)
	private Integer sourceAirportId;

	@Column(name = "destination_airport_id", nullable = false)
	private Integer destinationAirportId;

	@Column(name = "departure_time", nullable = false)
	private Instant departureTime;

	@Column(name = "arrival_time", nullable = false)
	private Instant arrivalTimue;

	@Column(name = "effective_from", nullable = false)
	private LocalDate effectiveFrom;

	@Column(name = "effective_to", nullable = false)
	private LocalDate effectiveTo;
}
