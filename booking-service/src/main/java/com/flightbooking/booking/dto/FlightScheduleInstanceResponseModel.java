package com.flightbooking.booking.dto;

import java.time.LocalDate;
import java.time.LocalTime;

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
public class FlightScheduleInstanceResponseModel {

	private Long recordId;

	private Long flightScheduleId;

	private LocalDate travelDate;

	private LocalTime actualDepartureTime;

	private LocalTime actualArrivalTime;

	private String status;

	private String gate;

	private String terminal;
}