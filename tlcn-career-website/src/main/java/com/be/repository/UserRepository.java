package com.be.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.be.model.User;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByPhone(String phone);

	Boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);
}
