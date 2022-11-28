package com.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.be.model.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

	List<Service> findByActive(Boolean active);

}
