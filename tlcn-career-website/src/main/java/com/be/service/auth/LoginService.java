package com.be.service.auth;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.be.controller.exception.CommonRuntimeException;
import com.be.dto.AdminDTO;
import com.be.dto.EmployerDTO;
import com.be.dto.UserDTO;
import com.be.payload.JwtResponse;
import com.be.repository.AdminRepository;
import com.be.repository.EmployerRepository;
import com.be.repository.UserRepository;
import com.be.utility.RolePrefix;
import com.be.utility.jwt.JwtUtils;

@Service
public class LoginService {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmployerRepository employerRepository;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	ModelMapper modelMapper;

	public JwtResponse<?> authenticateUser(String username, String password, String rolePrefix) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(rolePrefix + username, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtUtils.generateJwtToken(authentication, rolePrefix);
		String refreshToken = jwtUtils.generateJwtRefreshToken(authentication, rolePrefix);

		if (rolePrefix.equals(RolePrefix.ADMIN)) {
			AdminDetails admin = (AdminDetails) authentication.getPrincipal();

			return new JwtResponse<>(token, refreshToken,
					modelMapper.map(adminRepository.findByEmail(admin.getEmail()).get(), AdminDTO.class));
		} else if (rolePrefix.equals(RolePrefix.EMPLOYER)) {
			EmployerDetails employer = (EmployerDetails) authentication.getPrincipal();

			return new JwtResponse<>(token, refreshToken,
					modelMapper.map(employerRepository.findByEmail(employer.getEmail()).get(), EmployerDTO.class));
		} else if (rolePrefix.equals(RolePrefix.USER)) {
			UserDetails user = (UserDetails) authentication.getPrincipal();

			return new JwtResponse<>(token, refreshToken,
					modelMapper.map(userRepository.findByEmail(user.getEmail()).get(), UserDTO.class));
		}

		throw new CommonRuntimeException("Invalid RolePrefix");
	}
}
