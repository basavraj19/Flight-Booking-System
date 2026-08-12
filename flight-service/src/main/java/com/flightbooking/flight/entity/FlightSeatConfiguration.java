package com.flightbooking.flight.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
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
public class FlightSeatConfiguration extends BaseEntity {

	@Column(name = "flight_id")
	@NotNull
	private Long flightId;

	@Column(name = "seat_class_id")
	@NotNull
	private Long seatClassId;

	@Column(name = "total_seats")
	@NotNull
	private int totalSeats;
}
