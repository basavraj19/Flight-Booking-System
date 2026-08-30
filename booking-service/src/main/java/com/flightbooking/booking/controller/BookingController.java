package com.flightbooking.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.booking.dto.BookingRequestModel;
import com.flightbooking.booking.dto.BookingResponseModel;
import com.flightbooking.booking.service.BookingService;
import com.flightbooking.booking.util.BookingStatus;
import com.flightbooking.booking.util.JsonResponseEntity;
import com.flightbooking.booking.util.PaymentStatus;
import com.flightbooking.booking.util.StringConstants;
import com.flightbooking.booking.util.UrlConstants;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(UrlConstants.BOOKING)
@RequiredArgsConstructor
public class BookingController {

	private final BookingService bookingService;

	@PostMapping(UrlConstants.CREATE_BOOKING)
	public ResponseEntity<JsonResponseEntity<BookingResponseModel>> createNewBooking(
			@RequestBody final BookingRequestModel request) {

		JsonResponseEntity<BookingResponseModel> response = new JsonResponseEntity<>();

		final BookingResponseModel booking = bookingService.createNewBooking(request);

		if (BookingStatus.CONFIRMED.toString().equals(booking.getBookingStatus())
				&& PaymentStatus.SUCCESS.toString().equals(booking.getPaymentStatus())) {

			response.setStatus(StringConstants.success);
			response.setMessage(StringConstants.bookingCreatedSuccessMessage);
			response.setResult(booking);
			response.setException(null);
			response.setStatusCode(HttpStatus.CREATED);

			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}

		response.setStatus(StringConstants.failed);
		response.setMessage(StringConstants.bookingFailedMessage);
		response.setStatusCode(HttpStatus.BAD_REQUEST);

		response.setResult(booking);
		response.setException(null);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@GetMapping(UrlConstants.FETCH_BOOKING_DETAILS_BY_BOOKING_REF_NO)
	public ResponseEntity<JsonResponseEntity<BookingResponseModel>> FETCH_BOOKING_DETAILS_BY_BOOKING_REF_NO(
			@RequestParam(value = StringConstants.BookingRefNo) final String BookingRefNo) {

		JsonResponseEntity<BookingResponseModel> response = new JsonResponseEntity<>();

		final BookingResponseModel booking = bookingService.getBookingDetailsByBookingRefNo(BookingRefNo);

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordFetchSuccessMessage);
		response.setResult(booking);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
