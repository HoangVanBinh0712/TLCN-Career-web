package com.be.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.be.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	Optional<City> findByCode(String code);
}
