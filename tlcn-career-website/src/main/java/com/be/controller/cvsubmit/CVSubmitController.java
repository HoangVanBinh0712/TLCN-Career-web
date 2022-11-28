package com.be.controller.cvsubmit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.be.service.CVSubmitService;
import com.be.service.auth.EmployerDetails;
import com.be.service.auth.UserDetails;

@RestController
@RequestMapping("api")
public class CVSubmitController {

	@Autowired
	CVSubmitService cvSubmitService;

	@PostMapping("user/submitcv")
	public ResponseEntity<?> submit(@AuthenticationPrincipal UserDetails user, @RequestParam("postId") Long postId,
			@RequestParam("mediaId") Long mediaId) {
		return ResponseEntity.ok(cvSubmitService.sumbitCV(user.getId(), postId, mediaId));
	}

	@DeleteMapping("user/submitcv")
	public ResponseEntity<?> cancelSubmit(@AuthenticationPrincipal UserDetails user,
			@RequestParam("postId") Long postId, @RequestParam("mediaId") Long mediaId) {
		return ResponseEntity.ok(cvSubmitService.deleteSumbittedCV(user.getId(), postId, mediaId));
	}

	@GetMapping("employer/submitcv")
	public ResponseEntity<?> getSubmittedCv(@AuthenticationPrincipal EmployerDetails emp,
			@RequestParam("postId") Long postId, @RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "limit", required = false) Integer limit) {
		return ResponseEntity.ok(cvSubmitService.getListCV(emp.getId(), postId, page, limit));
	}
}
