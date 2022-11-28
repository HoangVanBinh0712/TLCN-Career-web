package com.be.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.be.model.Profile;
import com.be.model.pk.ProfilePK;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, ProfilePK> {

	@Query(value = "select * from profile where user_id = :user_id", nativeQuery = true)
	List<Profile> findByUserId(@Param("user_id") Long userId);
	
	@Transactional
	@Modifying
	@Query(value = "update profile set is_default = false where user_id = :user_id", nativeQuery = true)
	Integer updateDefaultToFalse(@Param("user_id") Long userId);
}
