package com.flightbooking.authservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightbooking.authservice.dto.LoginRequestDto;
import com.flightbooking.authservice.dto.UserAccountDto;
import com.flightbooking.authservice.entity.UserAccount;
import com.flightbooking.authservice.exception.DuplicateResourceException;
import com.flightbooking.authservice.exception.InvalidInputException;
import com.flightbooking.authservice.exception.UserNotFoundException;
import com.flightbooking.authservice.repository.UserRepository;
import com.flightbooking.authservice.util.JWTUtils;

import io.jsonwebtoken.lang.Arrays;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final JWTUtils jwtUtils;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		return userRepository.findUserByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " not found."));
	}

	@Transactional
	public UserAccountDto createNewUser(final UserAccountDto user)
			throws InvalidInputException, DuplicateResourceException {

		if (user == null) {
			throw new InvalidInputException("Invalid User.");
		}

		if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
			throw new InvalidInputException("Invalid Username/Password.");
		}

		Optional<UserAccount> account = userRepository.findUserByUsername(user.getUsername());

		if (account.isPresent()) {
			throw new DuplicateResourceException("User with username " + user.getUsername() + " already exists.");
		}

		String encodedPassword = passwordEncoder.encode(user.getPassword());

		UserAccount newUser = UserAccount.builder().username(user.getUsername()).password(encodedPassword)
				.firstName(user.getFirstName()).lastName(user.getLastName()).phoneNumber(user.getPhoneNumber())
				.role(user.getRole()).build();

		userRepository.save(newUser);

		return user;
	}

	public String loginUser(final LoginRequestDto request) throws InvalidInputException {
		if (request == null) {
			throw new InvalidInputException("Invalid User.");
		}

		if (request.getUsername().isEmpty() || request.getPassword().isEmpty()) {
			throw new InvalidInputException("Invalid Username/Password.");
		}

		Optional<UserAccount> account = userRepository.findUserByUsername(request.getUsername());

		if (account.isEmpty()) {
			throw new UserNotFoundException(request.getUsername() + " not found.");
		}

		boolean isValidPassword = passwordEncoder.matches(request.getPassword(), account.get().getPassword());

		if (!isValidPassword) {
			throw new InvalidInputException("Invalid Password.");
		}

		String[] roles = account.get().getRole().split(",");

		List<String> listOfRoles = Arrays.asList(roles);

		String token = jwtUtils.generateToken(request.getUsername(), listOfRoles);

		return token;
	}
}
