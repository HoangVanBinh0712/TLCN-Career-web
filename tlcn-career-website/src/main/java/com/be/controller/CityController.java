package com.be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.payload.ListResponse;
import com.be.repository.CityRepository;

@RestController
@RequestMapping("api/city")
public class CityController {

	@Autowired
	CityRepository cityRepository;

	@GetMapping
	public ResponseEntity<?> getCities() {
		return ResponseEntity.ok(new ListResponse<>(cityRepository.findAll()));
	}
}
