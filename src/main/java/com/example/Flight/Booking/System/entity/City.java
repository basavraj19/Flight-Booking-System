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
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class City extends BaseEntity {

	@Column(name = "city_name", unique = true, nullable = false)
	private String cityName;

	@Column(name = "country_id", nullable = false)
	private Long countryId;
}
