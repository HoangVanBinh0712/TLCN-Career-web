package com.be.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.dto.FieldDTO;
import com.be.service.FieldService;

@RestController
@RequestMapping("/api/admin/field")
public class FieldController {

	@Autowired
	FieldService fieldService;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid FieldDTO request) {
		return ResponseEntity.ok(fieldService.create(request));
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody @Valid FieldDTO request) {
		return ResponseEntity.ok(fieldService.update(request));
	}
}
