package com.flightbooking.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
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

	@Column(name = "seat_code")
	@NotNull
	private String seatCode;

	@Column(name = "name")
	@NotNull
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "display_order")
	@NotNull
	private int displayOrder;

	@Column(name = "active")
	@NotNull
	private boolean active;
}
