package com.flightbooking.booking.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.flightbooking.booking.dto.BookingPassengerRequestModel;
import com.flightbooking.booking.entity.Booking;
import com.flightbooking.booking.entity.BookingPassenger;
import com.flightbooking.booking.exception.InvalidInputException;
import com.flightbooking.booking.repository.BookingPassengerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PassengerService {

	private final BookingPassengerRepository bookingPassengerRepository;

	@Transactional
	public List<BookingPassenger> savePassengerDetails(final Booking booking,
			final List<BookingPassengerRequestModel> passengers) {

		if (passengers == null || passengers.isEmpty()) {
			throw new InvalidInputException("Please provide passenger details.");
		}

		if (passengers.size() != booking.getNoOfSeats()) {
			throw new InvalidInputException("Number of passengers must match the number of seats.");
		}

		List<BookingPassenger> bookingPassengers = new ArrayList<>();

		for (BookingPassengerRequestModel passenger : passengers) {

			validatePassenger(passenger);

			BookingPassenger bookingPassenger = BookingPassenger.builder().bookingId(booking.getId())
					.firstName(passenger.getFirstName().trim()).lastName(passenger.getLastName())
					.passportNumber(passenger.getPassportNumber()).age(passenger.getAge())
					.gender(passenger.getGender().trim().toUpperCase()).build();
			bookingPassengers.add(bookingPassenger);
		}

		return bookingPassengerRepository.saveAll(bookingPassengers);
	}

	private void validatePassenger(final BookingPassengerRequestModel passenger) {

		if (!StringUtils.hasText(passenger.getFirstName())) {
			throw new InvalidInputException("Passenger first name is required.");
		}

		if (passenger.getAge() == null || passenger.getAge() < 1 || passenger.getAge() > 120) {
			throw new InvalidInputException("Passenger age must be between 1 and 120.");
		}

		if (!StringUtils.hasText(passenger.getGender())) {
			throw new InvalidInputException("Passenger gender is required.");
		}
	}
}
