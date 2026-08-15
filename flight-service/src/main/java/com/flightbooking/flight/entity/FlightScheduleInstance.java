package com.flightbooking.flight.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.flightbooking.flight.util.FlightStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class FlightScheduleInstance extends BaseEntity {

	@Column(name = "flight_schedule_id")
	@NotNull
	private Long flightScheduleId;

	@Column(name = "travel_date")
	@NotNull
	private LocalDate travelDate;

	@Column(name = "actual_departure_time")
	private LocalTime actualDepartureTime;

	@Column(name = "actual_arrival_time")
	private LocalTime actualArrivalTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	@NotNull
	private FlightStatus status;

	@Column(name = "gate")
	private String gate;

	@Column(name = "terminal")
	private String terminal;
}
