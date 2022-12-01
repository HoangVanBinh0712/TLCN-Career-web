package com.be.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.be.model.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

	List<Service> findByActive(Boolean active);

	@Query(value = "select id from service where name like '%' :name '%'",nativeQuery = true)
	List<Long> findByName(@Param("name") String name);

}
