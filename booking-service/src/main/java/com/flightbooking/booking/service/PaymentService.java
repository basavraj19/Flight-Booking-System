package com.flightbooking.booking.service;

import org.springframework.stereotype.Service;

import com.flightbooking.booking.dto.PaymentRequestModel;
import com.flightbooking.booking.dto.PaymentResponseModel;
import com.flightbooking.booking.util.PaymentStatus;

@Service
public class PaymentService {

	
	public PaymentResponseModel processPayment(final PaymentRequestModel request) {

		// Mock payment processing
		return PaymentResponseModel.builder().paymentStatus(PaymentStatus.SUCCESS)
				.message("Payment processed successfully.").build();
		
		//return PaymentResponseModel.builder().paymentStatus(PaymentStatus.FAILED)
			//	.message("Something Went Wrong.").build();
	}
}
