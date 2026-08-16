package com.flightbooking.flight.dto;

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
public class SeatClassResponseModel {

	private Long recordId;
	
	private String seatCode;

	private String name;

	private String description;

	private int displayOrder;

	private boolean active;
}