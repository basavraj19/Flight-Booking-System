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
public class CountryResponseModel {

	private Long recordId;

	private String countryCode;

	private String countryName;

	private String createdBy;

	private String modifiedBy;
}
