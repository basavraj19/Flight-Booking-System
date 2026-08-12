package com.flightbooking.flight.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightSeatInventoryRequestModel {

	private Long FlightScheduleId;
	
	private BigDecimal price;

	private LocalDate travelDate;
}
