package com.be.controller.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.be.payload.BaseResponse;
import com.be.payload.auth.PasswordUpdateRequest;
import com.be.payload.auth.UserProfileUpdateRequest;
import com.be.service.UserService;
import com.be.service.auth.UserDetails;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

	@Autowired
	UserService userService;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> update(@AuthenticationPrincipal UserDetails user,
			@RequestPart("info") @Valid UserProfileUpdateRequest request,
			@RequestPart(name = "avatar", required = false) MultipartFile avatar) {
		return ResponseEntity.ok(userService.update(user.getId(), request, avatar));
	}

	@GetMapping
	public ResponseEntity<?> getUserInformation(@AuthenticationPrincipal UserDetails user) {
		return ResponseEntity.ok(userService.getUserProfile(user.getEmail()));
	}

	@GetMapping("loaduser")
	public ResponseEntity<?> getLoadUser(@AuthenticationPrincipal UserDetails user) {
		return ResponseEntity.ok(userService.getLoadUser(user.getEmail()));
	}

	@PutMapping(value = "/password")
	public ResponseEntity<?> changePassword(@AuthenticationPrincipal UserDetails user,
			@RequestBody @Valid PasswordUpdateRequest request) {
		return ResponseEntity.ok(userService.changePassword(user.getEmail(), request));
	}

	@PostMapping(value = "/cv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadCV(@AuthenticationPrincipal UserDetails user,
			@RequestPart(name = "CV") MultipartFile cv, @RequestPart(name = "name") String name,
			@RequestPart(name = "isDefault") Boolean isDefault) {
		if (cv.isEmpty() || !cv.getContentType().equals(MediaType.APPLICATION_PDF_VALUE))
			return ResponseEntity.ok(new BaseResponse(false, "Allow only pdf file !"));
		return ResponseEntity.ok(userService.uploadCV(user.getEmail(), cv, isDefault, name));
	}

	@DeleteMapping(value = "/cv")
	public ResponseEntity<?> uploadCV(@AuthenticationPrincipal UserDetails user,
			@RequestParam("mediaId") Long mediaId) {
		return ResponseEntity.ok(userService.deleteCV(user.getId(), mediaId));
	}

	@GetMapping(value = "/cv")
	public ResponseEntity<?> getListCV(@AuthenticationPrincipal UserDetails user) {
		return ResponseEntity.ok(userService.getListCV(user.getId()));
	}

	@GetMapping(value = "/cv/{id}")
	public ResponseEntity<?> getCVDetail(@AuthenticationPrincipal UserDetails user, @PathVariable("id") Long mediaId) {
		return ResponseEntity.ok(userService.getCVDetail(user.getId(), mediaId));
	}

	@GetMapping(value = "/submited/post")
	public ResponseEntity<?> getSubmittedPost(@AuthenticationPrincipal UserDetails user,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "limit", required = false) Integer limit) {
		return ResponseEntity.ok(userService.getCVSubmittedPost(user.getId(), page, limit));
	}
}
