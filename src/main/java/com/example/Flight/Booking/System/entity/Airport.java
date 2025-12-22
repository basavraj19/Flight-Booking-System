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
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Airport extends BaseEntity {

	@Column(name = "airport_name", unique = true, nullable = false)
	private String airport_name;

	@Column(name = "city_id", nullable = false)
	private Integer cityId;
}
