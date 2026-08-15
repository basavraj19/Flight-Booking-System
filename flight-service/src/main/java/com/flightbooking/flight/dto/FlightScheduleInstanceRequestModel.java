package com.flightbooking.flight.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightScheduleInstanceRequestModel {

	@NotNull
	private Long flightScheduleId;

	@NotNull
	private List<FlightScheduleInstanceDetailsRequestModel> instances;
}