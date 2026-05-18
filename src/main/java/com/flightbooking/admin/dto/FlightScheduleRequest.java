package com.flightbooking.admin.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightScheduleRequest {

	private Long flightScheduleId;

	private Long flightId;

	private Long sourceAirportId;

	private Long destinationAirportId;

	@Schema(type = "string", example = "00:00:00")
	private LocalTime departureTime;

	@Schema(type = "string", example = "00:00:00")
	private LocalTime arrivalTime;

	private LocalDate effectiveFrom;

	private LocalDate effectiveTo;
	
	private Byte arrivalDayOffset;
}
