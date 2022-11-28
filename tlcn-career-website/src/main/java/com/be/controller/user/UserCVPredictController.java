package com.be.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.be.service.CVPredictService;
import com.be.service.auth.UserDetails;

@RestController
public class UserCVPredictController {

	@Autowired
	CVPredictService cvPredictService;

	@GetMapping("/user/cvpredict")
	public ResponseEntity<?> getCVPredict(@AuthenticationPrincipal UserDetails user,
			@RequestParam("mediaId") Long mediaId) {
		return ResponseEntity.ok(cvPredictService.predict(mediaId, user.getId()));
	}

	@GetMapping("/user/cvpredict/post")
	public ResponseEntity<?> getPostPredictedForCV(@RequestParam("code") String code,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "limit", required = false) Integer limit) {
		return ResponseEntity.ok(cvPredictService.getPostByField(code, page, limit));
	}

}
