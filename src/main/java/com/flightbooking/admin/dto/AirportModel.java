package com.flightbooking.admin.dto;

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
public class AirportModel {

	private Long recordId;

	private String airportCode;

	private String airportName;

	private Long cityId;

	private String createdBy;

	private String modifiedBy;
}
