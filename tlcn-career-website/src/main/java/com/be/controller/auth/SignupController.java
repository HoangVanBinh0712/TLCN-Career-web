package com.be.controller.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.payload.auth.EmployerProfileCreateRequest;
import com.be.payload.auth.UserProfileCreateRequest;
import com.be.service.EmployerService;
import com.be.service.UserService;

@RestController
@RequestMapping("/api")
public class SignupController {
	@Autowired
	UserService userService;

	@Autowired
	EmployerService employerService;

	@PostMapping(value = "/user/signup")
	public ResponseEntity<?> createBuyer(@RequestBody @Valid UserProfileCreateRequest request) {
		return ResponseEntity.ok(userService.create(request));
	}

	@PostMapping(value = "/employer/signup")
	public ResponseEntity<?> createShop(@RequestBody @Valid EmployerProfileCreateRequest request) {

		return ResponseEntity.ok(employerService.create(request));
	}
}
