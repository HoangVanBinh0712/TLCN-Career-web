package com.be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.be.service.FieldService;
import com.be.utility.Page;

@RequestMapping("/api/field")
@RestController
public class CommonFieldController {

	@Autowired
	FieldService fieldService;

	@GetMapping
	public ResponseEntity<?> get(@RequestParam(name = "key", required =  false) String key,
			@RequestParam(name = "page_number", required =  false) Integer pageNumber, @RequestParam(name = "limit", required =  false) Integer limit) {
		return ResponseEntity.ok(fieldService.get(key, new Page(pageNumber, limit, fieldService.getCount(key))));
	}
}
