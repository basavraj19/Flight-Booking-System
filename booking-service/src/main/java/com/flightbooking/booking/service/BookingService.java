package com.flightbooking.booking.service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.flightbooking.booking.client.AdminService;
import com.flightbooking.booking.client.AuthService;
import com.flightbooking.booking.client.FlightService;
import com.flightbooking.booking.dto.BookingPassengerRequestModel;
import com.flightbooking.booking.dto.BookingRequestModel;
import com.flightbooking.booking.dto.BookingResponseModel;
import com.flightbooking.booking.dto.FlightScheduleInstanceResponseModel;
import com.flightbooking.booking.dto.PaymentRequestModel;
import com.flightbooking.booking.dto.PaymentResponseModel;
import com.flightbooking.booking.dto.SeatAvailabilityRequestModel;
import com.flightbooking.booking.dto.SeatAvailabilityResponseModel;
import com.flightbooking.booking.dto.SeatClassResponseModel;
import com.flightbooking.booking.entity.Booking;
import com.flightbooking.booking.entity.BookingPassenger;
import com.flightbooking.booking.exception.InvalidInputException;
import com.flightbooking.booking.exception.ResourceNotFoundException;
import com.flightbooking.booking.repository.BookingPassengerRepository;
import com.flightbooking.booking.repository.BookingRepository;
import com.flightbooking.booking.util.BookingStatus;
import com.flightbooking.booking.util.CommonUtils;
import com.flightbooking.booking.util.PaymentStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

	private static final String BOOKING_REFERENCE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	private static final SecureRandom RANDOM = new SecureRandom();

	private final PassengerService passengerService;

	private final BookingRepository bookingRepository;

	private final BookingPassengerRepository bookingPassengerRepository;

	private final FlightService flightService;

	private final AdminService adminService;

	private final AuthService authService;

	private final PaymentService paymentService;

	@Transactional
	public BookingResponseModel createNewBooking(final BookingRequestModel request) {

		validateIncomingRequest(request);

		final FlightScheduleInstanceResponseModel flightScheduleInstance = flightService
				.getFlightInstanceById(request.getFlightScheduleInstanceId()).getBody().getResult();

		if (flightScheduleInstance == null) {
			throw new ResourceNotFoundException(
					"Flight Schdule Instance with id " + request.getFlightScheduleInstanceId() + "not found.");
		}

		List<SeatClassResponseModel> seatDetails = adminService.getSeatDetails().getBody().getResult();
		boolean exists = seatDetails.stream().anyMatch(seat -> seat.getRecordId().equals(request.getSeatClassId()));

		if (!exists) {
			throw new InvalidInputException("Invalid Seat Class Id.");
		}

		SeatAvailabilityRequestModel model = SeatAvailabilityRequestModel.builder()
				.flightScheduleInstanceId(request.getFlightScheduleInstanceId()).seatClassId(request.getSeatClassId())
				.numberOfSeats(request.getPassengers().size()).build();

		SeatAvailabilityResponseModel seatAvailabilityDetails = flightService.reserveSeats(model).getBody().getResult();

		BigDecimal totalPaymentToProcess = seatAvailabilityDetails.getPricePerSeat()
				.multiply(BigDecimal.valueOf(seatAvailabilityDetails.getNumberOfSeats()));

		Long userId = authService.getUserByUsername(CommonUtils.getLogeedInUser()).getBody().getResult();

		Booking newBooking = Booking.builder().bookingReference(generateBookingReference()).userId(userId)
				.flightScheduleInstanceId(request.getFlightScheduleInstanceId()).seatClassId(request.getSeatClassId())
				.noOfSeats(seatAvailabilityDetails.getNumberOfSeats())
				.pricePerSeat(seatAvailabilityDetails.getPricePerSeat()).totalAmount(totalPaymentToProcess)
				.bookingStatus(BookingStatus.INITIATED).paymentStatus(PaymentStatus.PENDING).build();

		newBooking = bookingRepository.save(newBooking);

		List<BookingPassenger> passengers = passengerService.savePassengerDetails(newBooking, request.getPassengers());

		PaymentRequestModel paymentRequestModel = PaymentRequestModel.builder().bookingId(newBooking.getId())
				.amount(totalPaymentToProcess).build();

		PaymentResponseModel PaymentResponse = paymentService.processPayment(paymentRequestModel);

		BookingResponseModel response = new BookingResponseModel();

		if (PaymentResponse.getPaymentStatus() == PaymentStatus.SUCCESS) {
			response = handleSuccessBookingScenario(newBooking, passengers);
		} else {
			response = handleFailedBookingScenario(newBooking, passengers);
		}

		return response;
	}

	private void validateIncomingRequest(final BookingRequestModel request) {
		if (request == null) {
			throw new InvalidInputException("Invalid request object.");
		}

		if (request.getFlightScheduleInstanceId() == null || request.getFlightScheduleInstanceId() <= 0) {
			throw new InvalidInputException("Invalid Flight Schdule Instance Id.");
		}

		if (request.getSeatClassId() == null || request.getSeatClassId() <= 0) {
			throw new InvalidInputException("Invalid Seat Class Id.");
		}

		if (!CommonUtils.isValid(request.getPassengers())) {
			throw new InvalidInputException("Please provide valid passenger details.");
		}

	}

	private String generateBookingReference() {

		StringBuilder reference = new StringBuilder("BK");

		for (int i = 0; i < 8; i++) {
			reference
					.append(BOOKING_REFERENCE_CHARACTERS.charAt(RANDOM.nextInt(BOOKING_REFERENCE_CHARACTERS.length())));
		}

		return reference.toString();
	}

	private BookingResponseModel handleSuccessBookingScenario(final Booking booking,
			List<BookingPassenger> passengers) {

		booking.setBookingStatus(BookingStatus.CONFIRMED);
		booking.setPaymentStatus(PaymentStatus.SUCCESS);

		return mapToBookingResponse(booking, passengers);
	}

	private BookingResponseModel handleFailedBookingScenario(final Booking booking, List<BookingPassenger> passengers) {
		booking.setBookingStatus(BookingStatus.FAILED);
		booking.setPaymentStatus(PaymentStatus.FAILED);

		SeatAvailabilityRequestModel model = SeatAvailabilityRequestModel.builder()
				.flightScheduleInstanceId(booking.getFlightScheduleInstanceId()).seatClassId(booking.getSeatClassId())
				.numberOfSeats(booking.getNoOfSeats()).build();

		flightService.releaseSeats(model);

		return mapToBookingResponse(booking, passengers);
	}

	private BookingResponseModel mapToBookingResponse(final Booking booking, List<BookingPassenger> passengerDetails) {

		List<BookingPassengerRequestModel> passengers = passengerDetails.stream()
				.map(passenger -> BookingPassengerRequestModel.builder().firstName(passenger.getFirstName())
						.lastName(passenger.getLastName()).passportNumber(passenger.getPassportNumber())
						.age(passenger.getAge()).gender(passenger.getGender()).build())
				.toList();

		return BookingResponseModel.builder().bookingReference(booking.getBookingReference())
				.username(CommonUtils.getLogeedInUser()).flightScheduleInstanceId(booking.getFlightScheduleInstanceId())
				.seatClassId(booking.getSeatClassId()).noOfSeats(booking.getNoOfSeats())
				.pricePerSeat(booking.getPricePerSeat()).totalAmount(booking.getTotalAmount())
				.bookingStatus(booking.getBookingStatus().toString())
				.paymentStatus(booking.getPaymentStatus().toString()).passengers(passengers).build();
	}

	@Transactional(readOnly = true)
	public BookingResponseModel getBookingDetailsByBookingRefNo(final String bookingRefNo) {

		if (!StringUtils.hasText(bookingRefNo)) {
			throw new InvalidInputException("Invalid Booking Reference Number.");
		}

		final String bookingReference = bookingRefNo.trim();

		Booking bookingDetails = bookingRepository.findBookingBybookingReference(bookingReference).orElseThrow(
				() -> new ResourceNotFoundException("Booking with reference " + bookingReference + " not found."));

		List<BookingPassenger> passengerDetails = bookingPassengerRepository
				.findPasssengerDetailsByBookingId(bookingDetails.getId());

		if (passengerDetails.isEmpty()) {
			throw new ResourceNotFoundException("Passengers for booking " + bookingReference + " not found.");
		}

		return mapToBookingResponse(bookingDetails, passengerDetails);
	}
}
