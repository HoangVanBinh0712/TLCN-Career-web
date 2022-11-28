package com.be.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.be.model.Field;
import com.be.repository.custom.FieldCustomRepository;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long>, FieldCustomRepository {

	Optional<Field> findByCode(String code);

	Boolean existsByCode(String code);

	@Query(nativeQuery = true, value = "SELECT * FROM field as e WHERE e.code IN (:names)")
	List<Field> findByListCode(@Param("names") Set<String> names);

	@Query(nativeQuery = true, value = "SELECT * FROM field as e WHERE e.name = :name limit 1")
	Field findByName(@Param("name") String name);
}
