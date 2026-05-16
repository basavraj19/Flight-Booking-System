package com.flightbooking.admin.dto;

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
public class FlightModel {

	private Long flightId;

	private String flightNumber;

	private Integer airlineId;

	private String createdBy;

	private String modifiedBy;
}
