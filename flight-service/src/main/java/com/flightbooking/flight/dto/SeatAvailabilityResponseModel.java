package com.flightbooking.flight.dto;


import java.math.BigDecimal;

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
public class SeatAvailabilityResponseModel {

    private Long flightScheduleInstanceId;

    private Long seatClassId;

    private int numberOfSeats;

    private int availableSeats;

    private BigDecimal pricePerSeat;
}