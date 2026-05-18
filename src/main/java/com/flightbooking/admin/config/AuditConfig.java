package com.flightbooking.admin.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component("auditConfig")
public class AuditConfig implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {

		return Optional.of("Admin");
	}
}
