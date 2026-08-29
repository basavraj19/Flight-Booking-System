package com.flightbooking.booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
@Entity
public class BookingPassenger extends BaseEntity {

	@Column(name = "booking_id")
	@NotNull
	private Long bookingId;

	@Column(name = "first_name", length = 100)
	@NotNull
	private String firstName;

	@Column(name = "last_name", length = 100)
	private String lastName;

	@Column(name = "age")
	@NotNull
	@Min(1)
	@Max(120)
	private int age;

	@Column(name = "gender", length = 20)
	@NotNull
	private String gender;

	@Column(name = "passport_number", length = 50)
	private String passportNumber;
}