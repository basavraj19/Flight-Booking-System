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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "city", uniqueConstraints = {
		@UniqueConstraint(name = "uk_city_name_country", columnNames = { "city_name", "country_id" }) })
public class City extends BaseEntity {

	@Column(name = "city_code", unique = true, nullable = false, length = 10)
	private String cityCode;

	@Column(name = "city_name", nullable = false)
	private String cityName;

	@Column(name = "country_id", nullable = false)
	private Long countryId;
}
