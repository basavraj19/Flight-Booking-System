package com.flightbooking.authservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightbooking.authservice.dto.LoginRequestDto;
import com.flightbooking.authservice.dto.UpdatePasswordRequest;
import com.flightbooking.authservice.dto.UpdateUserRequestDto;
import com.flightbooking.authservice.dto.UserAccountDto;
import com.flightbooking.authservice.entity.UserAccount;
import com.flightbooking.authservice.exception.DuplicateResourceException;
import com.flightbooking.authservice.exception.InvalidInputException;
import com.flightbooking.authservice.exception.UserNotFoundException;
import com.flightbooking.authservice.repository.UserRepository;
import com.flightbooking.authservice.util.JWTUtils;

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
	public UserAccountDto createNewUser(final UserAccountDto request)
			throws InvalidInputException, DuplicateResourceException {

		Optional<UserAccount> account = userRepository.findUserByUsername(request.getUsername());

		if (account.isPresent()) {
			throw new DuplicateResourceException("User with username " + request.getUsername() + " already exists.");
		}

		String encodedPassword = passwordEncoder.encode(request.getPassword());

		UserAccount user = UserAccount.builder().username(request.getUsername()).password(encodedPassword)
				.firstName(request.getFirstName()).lastName(request.getLastName()).phoneNumber(request.getPhoneNumber())
				.role(request.getRole()).build();

		userRepository.save(user);

		return UserAccountDto.builder().username(user.getUsername()).firstName(user.getFirstName())
				.lastName(user.getLastName()).phoneNumber(user.getPhoneNumber()).role(user.getRole()).build();
	}

	@Transactional(readOnly = true)
	public String loginUser(final LoginRequestDto request) throws InvalidInputException {

		UserAccount user = validateAndGetUserDetails(request.getUsername(), request.getPassword());

		List<String> roles = Arrays.stream(user.getRole().split(",")).map(String::trim).toList();

		String token = jwtUtils.generateToken(request.getUsername(), roles);

		return token;
	}

	@Transactional(readOnly = true)
	public UserAccountDto getUserByUsername(final String username) throws InvalidInputException {

		if (username == null || username.isBlank()) {
			throw new InvalidInputException("Invalid Username.");
		}

		UserAccount user = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username + " not found."));

		return UserAccountDto.builder().username(username).firstName(user.getFirstName()).lastName(user.getLastName())
				.phoneNumber(user.getPhoneNumber()).role(user.getRole()).build();
	}

	@Transactional
	public void updateUserPassword(final UpdatePasswordRequest request) throws InvalidInputException {

		UserAccount user = validateAndGetUserDetails(request.getUsername(), request.getOldPassword());

		if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
			throw new InvalidInputException("New password cannot be the same as the old password.");
		}

		String encodePassowd = passwordEncoder.encode(request.getNewPassword());
		user.setPassword(encodePassowd);

		userRepository.save(user);
	}

	private UserAccount validateAndGetUserDetails(final String username, final String password)
			throws InvalidInputException {
		UserAccount user = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username + " not found."));

		boolean isValidPassword = passwordEncoder.matches(password, user.getPassword());

		if (!isValidPassword) {
			throw new InvalidInputException("Invalid Password.");
		}
		return user;
	}

	@Transactional
	public void deleteUserAccount(final LoginRequestDto request) {

		UserAccount user = validateAndGetUserDetails(request.getUsername(), request.getPassword());

		userRepository.deleteById(user.getId());
	}

	@Transactional
	public UpdateUserRequestDto updateUserDetails(final UpdateUserRequestDto user) {
		UserAccount existingUser = userRepository.findUserByUsername(user.getUsername())
				.orElseThrow(() -> new UserNotFoundException(user.getUsername() + " not found."));

		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setPhoneNumber(user.getPhoneNumber());

		userRepository.save(existingUser);

		return UpdateUserRequestDto.builder().username(existingUser.getUsername())
				.firstName(existingUser.getFirstName()).lastName(existingUser.getLastName())
				.phoneNumber(existingUser.getPhoneNumber()).build();
	}
}
