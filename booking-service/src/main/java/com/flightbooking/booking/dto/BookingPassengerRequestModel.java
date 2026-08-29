package com.flightbooking.booking.dto;

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
public class BookingPassengerRequestModel {

	private String firstName;

	private String lastName;

	private String passportNumber;

	private Integer age;

	private String gender;
}
