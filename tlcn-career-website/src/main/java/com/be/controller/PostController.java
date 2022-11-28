package com.be.controller;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.be.service.AdminPostService;
import com.be.service.PostService;
import com.be.utility.ModelSorting;
import com.be.utility.Page;
import com.be.utility.datatype.ESalary;

@RequestMapping("api/post")
@RestController
public class PostController {

	@Autowired
	PostService postService;

	@Autowired
	AdminPostService adminPostService;

	@GetMapping("/{postId}")
	public ResponseEntity<?> getPostDetail(@PathVariable("postId") Long postId) {
		return ResponseEntity.ok(postService.getDetail(postId));
	}

	@GetMapping
	public ResponseEntity<?> getPost(@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "recruit", required = false) Long recruit,
			@RequestParam(name = "salary", required = false) Long salary,
			@RequestParam(name = "eSalary", required = false) ESalary eSalary,
			@RequestParam(name = "authorId", required = false) Long authorId,
			@RequestParam(name = "fieldId", required = false) Long fieldId,
			@RequestParam(name = "cityId", required = false) Long cityId,
			@RequestParam(name = "expirationDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date expirationDate,
			@RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam(name = "serviceId", required = false) Long serviceId,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "limit", required = false) Integer limit,
			@RequestParam(required = false) Integer sortBy,
			@RequestParam(required = false) Boolean sortDescending)
			throws ParseException {

		Long count = adminPostService.getCountBeforSearch(keyword, recruit, salary, eSalary, authorId, fieldId, cityId,
				null, expirationDate, startDate, serviceId);

		return ResponseEntity.ok(adminPostService.search(keyword, recruit, salary, eSalary, authorId, fieldId,
				cityId, null, expirationDate, startDate, serviceId, new Page(page, limit, count.intValue(), ModelSorting.getPostSort(sortBy, sortDescending))));
	}

}
