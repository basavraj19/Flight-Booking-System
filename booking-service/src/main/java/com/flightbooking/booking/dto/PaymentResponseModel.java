package com.flightbooking.booking.dto;

import com.flightbooking.booking.util.PaymentStatus;

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
public class PaymentResponseModel {

	private PaymentStatus paymentStatus;

	private String message;
}