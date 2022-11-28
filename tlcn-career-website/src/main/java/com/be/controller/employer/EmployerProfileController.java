package com.be.controller.employer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.be.payload.auth.EmployerProfileUpdateRequest;
import com.be.payload.auth.PasswordUpdateRequest;
import com.be.service.EmployerService;
import com.be.service.auth.EmployerDetails;

@RestController
@RequestMapping("/api/employer")
public class EmployerProfileController {
	@Autowired
	EmployerService employerService;

	@PutMapping(value = "/password")
	public ResponseEntity<?> changePassword(@AuthenticationPrincipal EmployerDetails employer,
			@RequestBody @Valid PasswordUpdateRequest request) {
		return ResponseEntity.ok(employerService.changePassword(employer.getEmail(), request));
	}

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> update(@AuthenticationPrincipal EmployerDetails employer,
			@RequestPart("info") @Valid EmployerProfileUpdateRequest request,
			@RequestPart(name = "avatar", required = false) MultipartFile avatar) {
		return ResponseEntity.ok(employerService.update(employer.getId(),request, avatar));
	}
	@GetMapping("/infomation/{id}")
	public ResponseEntity<?> getEmployerInfomation(@PathVariable("id")Long id) {
		return ResponseEntity.ok(employerService.getEmployerById(id));
	}
	@GetMapping
	public ResponseEntity<?> getUserInformation(@AuthenticationPrincipal EmployerDetails employer) {
		return ResponseEntity.ok(employerService.getEmployerProfile(employer.getEmail()));
	}
}
