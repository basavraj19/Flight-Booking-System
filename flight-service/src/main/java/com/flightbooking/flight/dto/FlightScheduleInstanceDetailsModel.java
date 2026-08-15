package com.flightbooking.flight.dto;

import java.time.LocalDate;
import java.util.List;

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
public class FlightScheduleInstanceDetailsModel {
	
	private LocalDate travelDate;
	
	private List<FlightSeatPriceModel> seatPrices;
}
