package com.flightbooking.admin.config;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.flightbooking.admin.util.JWTUtils;

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

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

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

			if (SecurityContextHolder.getContext().getAuthentication() == null && jwtUtils.isTokenValid(token)) {

				String username = jwtUtils.extractUserName(token);

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
						jwtUtils.extractRoles(token));

				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(auth);
			}

		} catch (Exception e) {
			SecurityContextHolder.clearContext();

			jwtAuthenticationEntryPoint.commence(request, response,
					new BadCredentialsException("Invalid or expired JWT token", e));

			return;
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
