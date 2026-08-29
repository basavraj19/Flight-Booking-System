package com.flightbooking.booking.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonUtils {

	public static boolean isValid(final Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof String str) {
			return !str.trim().isEmpty();
		}

		if (obj instanceof Collection<?> c) {
			return !c.isEmpty();
		}

		if (obj instanceof Map<?, ?> m) {
			return !m.isEmpty();
		}

		if (obj.getClass().isArray()) {
			return Array.getLength(obj) > 0;
		}

		return true;
	}

	public static String getLogeedInUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return authentication.getName();
	}
}
