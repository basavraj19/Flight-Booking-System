package com.flightbooking.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditConfig")
public class FlightBookingAdminServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightBookingAdminServiceApplication.class, args);
	}

}
