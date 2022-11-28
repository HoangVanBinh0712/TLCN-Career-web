package com.be.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.be.model.Admin;
import com.be.model.Employer;
import com.be.model.User;
import com.be.repository.AdminRepository;
import com.be.repository.EmployerRepository;
import com.be.repository.UserRepository;
import com.be.service.auth.AdminDetails;
import com.be.service.auth.EmployerDetails;
import com.be.service.auth.UserDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	EmployerRepository employerRepository;

	@Override
	@Transactional
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String usernameWithRolePrefix)
			throws UsernameNotFoundException {
		String role = usernameWithRolePrefix.substring(0, usernameWithRolePrefix.indexOf('@'));
		String email = usernameWithRolePrefix.substring(usernameWithRolePrefix.indexOf('@') + 1,
				usernameWithRolePrefix.length());

		switch (role) {
		case "admin":
			Admin admin = adminRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
					String.format("%s user not found with email: %s", role, email)));
			return AdminDetails.build(admin);
		case "user":
			User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
					String.format("%s user not found with email: %s", role, email)));
			return UserDetails.build(user);
		case "employer":
			Employer employer = employerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
					String.format("%s user not found with email: %s", role, email)));
			return EmployerDetails.build(employer);
		default:
			throw new UsernameNotFoundException(String.format("Invalid role prefix: %s", role));
		}
	}
}
