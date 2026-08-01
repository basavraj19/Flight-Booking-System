package com.flightbooking.flight.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.AssertTrue;
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
public class FlightSchedule extends BaseEntity {

	@Column(name = "flight_id")
	@NotNull
	private Long flightId;

	@Column(name = "source_airport_id")
	@NotNull
	private Long sourceAirportId;

	@Column(name = "destination_airport_id")
	@NotNull
	private Long destinationAirportId;

	@Column(name = "scheduled_departure_time")
	@NotNull
	private LocalTime scheduledDepartureTime;

	@Column(name = "scheduled_arrival_time")
	@NotNull
	private LocalTime scheduleArrivalTime;

	@Column(name = "effective_from")
	@NotNull
	private LocalDate effectiveFrom;

	@Column(name = "effective_to")
	@NotNull
	private LocalDate effectiveTo;

	@Column(name = "arrival_day_offset")
	private Byte arrivaleDayOffset;

	@AssertTrue(message = "Arrival time must be greater than departure time.")
	public boolean isArrivalTimeValid() {

		if (scheduledDepartureTime == null || scheduleArrivalTime == null || arrivaleDayOffset == null) {
			return true;
		}

		if (arrivaleDayOffset == 0) {
			return scheduleArrivalTime.isAfter(scheduledDepartureTime);
		} else {
			return scheduleArrivalTime.isBefore(scheduledDepartureTime);
		}
	}

	@AssertTrue(message = "Effective to date must be greater than or equal to Effective from date.")
	public boolean isEffectiveDateValid() {
		if (effectiveFrom == null || effectiveTo == null) {
			return true;
		}

		return !effectiveTo.isBefore(effectiveFrom);
	}

	@AssertTrue(message = "Source and destination airports can not be same.")
	public boolean isSourceDestinationValid() {
		if (sourceAirportId == null || destinationAirportId == null) {
			return true;
		}

		return !sourceAirportId.equals(destinationAirportId);
	}
}
