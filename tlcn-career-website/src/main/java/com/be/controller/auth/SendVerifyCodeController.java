package com.be.controller.auth;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.be.service.EmployerService;
import com.be.service.SendEmailService;
import com.be.service.UserService;
import com.be.utility.EmailType;
import com.be.utility.RolePrefix;

@RestController
@RequestMapping("api")
public class SendVerifyCodeController {

	@Autowired
	SendEmailService sendEmailService;

	@Autowired
	UserService userService;

	@Autowired
	EmployerService empService;

	@GetMapping("/send-user-verify-code")
	public ResponseEntity<?> sendUserCode(@NotBlank String email) {
		return ResponseEntity.ok(sendEmailService.sendVerifyCode(RolePrefix.USER, email, EmailType.CONFIRM_EMAIL));
	}
	@GetMapping("/send-employer-verify-code")
	public ResponseEntity<?> sendEmployerCode(@NotBlank String email) {
		return ResponseEntity.ok(sendEmailService.sendVerifyCode(RolePrefix.EMPLOYER, email, EmailType.CONFIRM_EMAIL));
	}
	@PostMapping("user/confirm-email")
	public ResponseEntity<?> confirmEmailUser(@RequestParam(name = "email", required = true) String email,
			@RequestParam(name = "code", required = true) String code) {
		return ResponseEntity.ok(userService.emailConfirm(email, code));
	}

	@PostMapping("employer/confirm-email")
	public ResponseEntity<?> confirmEmailEmployer(@RequestParam(name = "email", required = true) String email,
			@RequestParam(name = "code", required = true) String code) {
		return ResponseEntity.ok(empService.emailConfirm(email, code));
	}

	@GetMapping("/send-user-reset-password-code")
	public ResponseEntity<?> sendUserRPCode(@NotBlank String email) {
		return ResponseEntity.ok(userService.sendResetPasswordCode(email));
	}

	@GetMapping("/send-employer-reset-password-code")
	public ResponseEntity<?> sendEmployerRPCode(@NotBlank String email) {
		return ResponseEntity.ok(empService.sendResetPasswordCode(email));
	}
}
