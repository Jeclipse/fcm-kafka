package com.montymobile.registration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.montymobile.registration.domain.RegistrationToken;

public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {
	
	Optional<RegistrationToken> findByTopicAndToken(String topic, String token);
}