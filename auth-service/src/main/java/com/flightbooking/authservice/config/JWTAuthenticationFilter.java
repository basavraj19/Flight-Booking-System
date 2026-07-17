package com.flightbooking.authservice.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.flightbooking.authservice.util.JWTUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private final JWTUtils jwtUtils;

	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			filterChain.doFilter(request, response);
			return;
		}
		String token = getJwtToken(cookies);

		if (token == null || token.isEmpty()) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			String username = jwtUtils.extractUserName(token);

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				if (jwtUtils.isTokenValid(token, userDetails)) {

					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());

					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}

		} catch (Exception e) {
			System.out.println("Invalid JWT: " + e.getMessage());
		}

		filterChain.doFilter(request, response);
	}

	private String getJwtToken(Cookie[] cookies) {
		for (Cookie cookie : cookies) {
			if ("jwtToken".equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}
}
