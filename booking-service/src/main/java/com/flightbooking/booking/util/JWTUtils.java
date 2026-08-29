package com.flightbooking.booking.util;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtils {

	@Value("${secretKey}")
	private String secretKey;

	public SecretKey getKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	public Claims extractAllClaims(final String token) {
		if (token == null || token.trim().isEmpty()) {
			throw new IllegalArgumentException("JWT token cannot be null or blank");
		}

		return Jwts.parser().verifyWith((SecretKey) getKey()).build().parseSignedClaims(token).getPayload();
	}

	public <T> T extractClaims(final String token, Function<Claims, T> resolver) {
		return resolver.apply(extractAllClaims(token));
	}

	@SuppressWarnings("unchecked")
	public List<GrantedAuthority> extractRoles(final String token) {
		Claims claim = extractAllClaims(token);
		List<String> roleStrings = claim.get("roles", List.class);

		List<GrantedAuthority> authorities = roleStrings.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());

		return authorities;
	}

	public String extractUserName(final String token) {
		return extractClaims(token, Claims::getSubject);
	}

	public boolean isTokenValid(String token) {
		try {
			extractAllClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}
