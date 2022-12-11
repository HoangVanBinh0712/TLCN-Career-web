package com.be.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.dto.CityDTO;
import com.be.service.impl.CityService;


@RestController
@RequestMapping("/api/admin/city")
public class AdminCityController {
    @Autowired
    CityService cityService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CityDTO request) {
        return ResponseEntity.ok(cityService.create(request));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid CityDTO request) {
        return ResponseEntity.ok(cityService.update(request));
    }
}
