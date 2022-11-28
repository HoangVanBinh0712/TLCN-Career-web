package com.be.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.be.model.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
	Boolean existsByPhone(String phone);

	Boolean existsByEmail(String email);

	Optional<Employer> findByEmail(String email);

}
