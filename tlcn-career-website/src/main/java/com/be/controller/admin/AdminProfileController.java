package com.be.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.service.AdminService;
import com.be.service.auth.AdminDetails;

@RestController
@RequestMapping("/api/admin")
public class AdminProfileController {

	@Autowired
	AdminService adminService;

	@GetMapping
	public ResponseEntity<?> getAdminProfile(@AuthenticationPrincipal AdminDetails admin) {
		return ResponseEntity.ok(adminService.getAdminProfile(admin.getEmail()));
	}
}
