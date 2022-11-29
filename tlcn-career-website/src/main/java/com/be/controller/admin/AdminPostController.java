package com.be.controller.admin;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.be.service.AdminPostService;
import com.be.service.PostSearchService;
import com.be.service.auth.AdminDetails;
import com.be.utility.ModelSorting;
import com.be.utility.Page;
import com.be.utility.datatype.ESalary;
import com.be.utility.datatype.EStatus;

@RestController
@RequestMapping("api/admin/post")
public class AdminPostController {

	@Autowired
	AdminPostService adminPostService;

	@Autowired
	PostSearchService postSearchService;

	@GetMapping("{postId}")
	public ResponseEntity<?> getDetail(@PathVariable("postId") Long postId) {
		return ResponseEntity.ok(adminPostService.getPostDetail(postId));
	}

	@GetMapping
	public ResponseEntity<?> getPost(@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "recruit", required = false) Long recruit,
			@RequestParam(name = "salary", required = false) Long salary,
			@RequestParam(name = "eSalary", required = false) ESalary eSalary,
			@RequestParam(name = "authorId", required = false) Long authorId,
			@RequestParam(name = "fieldId", required = false) Long fieldId,
			@RequestParam(name = "cityId", required = false) Long cityId,
			@RequestParam(name = "status", required = false) EStatus status,
			@RequestParam(name = "expirationDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date expirationDate,
			@RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam(name = "serviceId", required = false) Long serviceId,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "limit", required = false) Integer limit,
			@RequestParam(required = false) Integer sortBy,
			@RequestParam(required = false) Boolean sortDescending) throws ParseException {

		Long count = postSearchService.getCountBeforSearch(keyword, recruit, salary, eSalary, authorId, fieldId, cityId,
				status, expirationDate, startDate, serviceId);

		return ResponseEntity.ok(postSearchService.search(keyword, recruit, salary, eSalary, authorId, fieldId, cityId,
				status, expirationDate, startDate, serviceId,
				new Page(page, limit, count.intValue(), ModelSorting.getPostSort(sortBy, sortDescending))));
	}

	@PutMapping("/accept/{postId}")
	public ResponseEntity<?> accept(@AuthenticationPrincipal AdminDetails admin, @PathVariable("postId") Long postId) {
		return ResponseEntity.ok(adminPostService.acceptPost(admin.getEmail(), postId));
	}

	@PutMapping("/unaccept/{postId}")
	public ResponseEntity<?> unAccept(@PathVariable("postId") Long postId) {
		return ResponseEntity.ok(adminPostService.unAcceptPost(postId));
	}

	@GetMapping("statistic")
	public ResponseEntity<?> statistc() {
		return ResponseEntity.ok(adminPostService.getStatistic());
	}
}
