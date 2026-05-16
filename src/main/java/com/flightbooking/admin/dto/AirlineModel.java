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
public class AirlineModel {

	private Long recordId;

	private String airlineName;

	private String createBy;

	private String modifiedBy;
}
