package com.flightbooking.flight.dto;

import com.flightbooking.flight.util.FlightStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFlightScheduleInstanceStatusRequestModel {

	@NotNull
	private FlightStatus status;
}
