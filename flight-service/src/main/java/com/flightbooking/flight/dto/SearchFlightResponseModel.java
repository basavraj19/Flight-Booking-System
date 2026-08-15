package com.flightbooking.flight.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class SearchFlightResponseModel {

	private Long flightScheduleInstanceId;

	private String flightNumber;

	private String airlineName;

	private String sourceAirportCode;

	private String destinationAirportCode;

	@Schema(type = "string", example = "10:30:00")
	private LocalTime departureTime;

	@Schema(type = "string", example = "12:45:00")
	private LocalTime arrivalTime;

	private Byte arrivalDayOffset;

	private LocalDate travelDate;

	private List<FlightSeatPriceModel> seatPrices;
}
