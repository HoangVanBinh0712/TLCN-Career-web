package com.be.controller.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.payload.auth.LoginRequest;
import com.be.service.auth.LoginService;
import com.be.utility.RolePrefix;
import com.be.utility.jwt.JwtUtils;

@RestController
@RequestMapping("/api")
public class LoginController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	LoginService loginService;

	@PostMapping("/admin/login")
	public ResponseEntity<?> authenticateUsers(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity
				.ok(loginService.authenticateUser(request.getUsername(), request.getPassword(), RolePrefix.ADMIN));
	}

	@PostMapping("/user/login")
	public ResponseEntity<?> authenticateBuyer(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity
				.ok(loginService.authenticateUser(request.getUsername(), request.getPassword(), RolePrefix.USER));
	}

	@PostMapping("/employer/login")
	public ResponseEntity<?> authenticateShop(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity
				.ok(loginService.authenticateUser(request.getUsername(), request.getPassword(), RolePrefix.EMPLOYER));
	}
}
