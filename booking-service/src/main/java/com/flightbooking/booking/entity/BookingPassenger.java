package com.flightbooking.booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookingPassenger extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "booking_id", nullable = false)
	@NotNull
	private Booking booking;

	@Column(name = "first_name", nullable = false, length = 100)
	@NotNull
	private String firstName;

	@Column(name = "last_name", length = 100)
	private String lastName;

	@Column(name = "age", nullable = false)
	@NotNull
	private int age;

	@Column(name = "gender", nullable = false, length = 20)
	@NotNull
	private String gender;

	@Column(name = "passport_number", length = 50)
	private String passportNumber;
}