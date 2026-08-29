package com.flightbooking.booking.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseModel {

	private String bookingReference;

	private String username;

	private Long flightScheduleInstanceId;

	private Long seatClassId;

	private int noOfSeats;

	private BigDecimal pricePerSeat;

	private BigDecimal totalAmount;

	private String bookingStatus;

	private String paymentStatus;

	private List<BookingPassengerRequestModel> passengers;
}
