package com.example.Flight.Booking.System.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Country extends BaseEntity {

	@Column(name = "country_code", unique = true, nullable = false)
	private String countryCode;

	@Column(name = "country_name", unique = true, nullable = false)
	private String countryName;
}
