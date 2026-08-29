package com.flightbooking.booking.dto;

import java.util.List;

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
public class BookingRequestModel {

	private Long flightScheduleInstanceId;

	private Long seatClassId;

	private List<BookingPassengerRequestModel> passengers;
}
