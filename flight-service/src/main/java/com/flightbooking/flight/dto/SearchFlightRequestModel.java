package com.flightbooking.flight.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
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
public class SearchFlightRequestModel {

	@NotNull
	private String sourceCityCode;

	@NotNull
	private String destinationCityCode;

	@NotNull
	private LocalDate travelDate;
	
}
