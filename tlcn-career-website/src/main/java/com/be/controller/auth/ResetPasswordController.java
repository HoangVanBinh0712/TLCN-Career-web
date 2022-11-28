package com.be.controller.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.payload.auth.ResetPasswordRequest;
import com.be.service.EmployerService;
import com.be.service.UserService;

@RestController
@RequestMapping("api")
public class ResetPasswordController {
	@Autowired
	UserService userService;

	@Autowired
	EmployerService empService;

	@PostMapping("user/reset-password")
	public ResponseEntity<?> userResetPasswordWithCode(@RequestBody @Valid ResetPasswordRequest request) {
		return ResponseEntity.ok(userService.changePasswordWithResetCode(request));
	}

	@PostMapping("employer/reset-password")
	public ResponseEntity<?> employerResetPasswordWithCode(@RequestBody @Valid ResetPasswordRequest request) {
		return ResponseEntity.ok(empService.changePasswordWithResetCode(request));
	}

}
