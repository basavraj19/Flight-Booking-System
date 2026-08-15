package com.flightbooking.flight.dto;

import java.math.BigDecimal;

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
public class FlightSeatPriceRequestModel {
	
	private Long seatTypeId;

	private BigDecimal price;
}
