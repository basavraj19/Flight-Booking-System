package com.flightbooking.flight.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
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
public class FlightScheduleInstance extends BaseEntity {

	@Column(name = "flight_Schedule_id")
	@NotNull
	private Long flightScheduleId;

	@Column(name = "travel_date")
	@NotNull
	private LocalDate travelDate;

	@Column(name = "actual_departure_time")
	@NotNull
	private LocalTime actualDepartureTime;

	@Column(name = "actual_arrival_time")
	@NotNull
	private LocalTime actualArrivalTime;

	@Column(name = "status")
	@NotNull
	private String status;

	@Column(name = "gate")
	@NotNull
	private String gate;

	@Column(name = "terminal")
	@NotNull
	private String terminal;
}
