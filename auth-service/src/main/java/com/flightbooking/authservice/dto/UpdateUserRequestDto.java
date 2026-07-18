package com.flightbooking.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDto {

	@NotBlank(message = "Username is required.")
	private String username;

	private String firstName;

	private String lastName;

	private String phoneNumber;
}
