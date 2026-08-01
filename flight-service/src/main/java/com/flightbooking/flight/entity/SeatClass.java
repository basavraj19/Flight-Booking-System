package com.flightbooking.flight.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class SeatClass extends BaseEntity {

	@Column(name = "seat_code", updatable = false)
	private String seatCode;

	@Column(name = "name", updatable = false)
	private String name;

	@Column(name = "description", updatable = false)
	private String description;

	@Column(name = "display_order", updatable = false)
	private int displayOrder;

	@Column(name = "active", updatable = false)
	private boolean active;
}
