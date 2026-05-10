package com.flightbooking.admin.entity;

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
public class Flight extends BaseEntity {

	@Column(name = "flight_number", unique = true, nullable = false)
	private String flightNumber;

	@Column(name = "airline_id", nullable = false)
	private Integer airlineId;
}
