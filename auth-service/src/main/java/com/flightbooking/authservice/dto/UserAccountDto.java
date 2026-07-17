package com.flightbooking.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDto {

	private String username;

	private String password;

	private String firstName;

	private String lastName;

	private String phoneNumber;

	private String role;
}
