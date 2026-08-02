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
public class SeatClassResponseModel {

	private String seatCode;

	private String name;

	private String description;

	private int displayOrder;

	private boolean active;
}