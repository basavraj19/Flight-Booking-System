package com.flightbooking.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "airport", uniqueConstraints = {
		@UniqueConstraint(name = "uk_airport_name_city", columnNames = { "airport_name", "city_id" }) })
public class Airport extends BaseEntity {

	@Column(name = "airport_code", unique = true, nullable = false, length = 3)
	private String airportCode;

	@Column(name = "airport_name", nullable = false)
	private String airportName;

	@Column(name = "city_id", nullable = false)
	private Long cityId;
}
