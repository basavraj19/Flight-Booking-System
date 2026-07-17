package com.flightbooking.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightbooking.authservice.entity.UserAccount;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {

	Optional<UserAccount> findUserByUsername(String username);
}
