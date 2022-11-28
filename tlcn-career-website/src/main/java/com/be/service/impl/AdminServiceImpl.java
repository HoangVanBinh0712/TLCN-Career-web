package com.be.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.controller.exception.CommonRuntimeException;
import com.be.dto.AdminDTO;
import com.be.model.Admin;
import com.be.repository.AdminRepository;
import com.be.service.AdminService;


@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository adminRepo;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public AdminDTO getAdminProfile(String email) {
		Optional<Admin> optAdmin = adminRepo.findByEmail(email);
		if (optAdmin.isEmpty())
			throw new CommonRuntimeException("User not found with Admin: " + email);
		return modelMapper.map(optAdmin.get(), AdminDTO.class);

	}

}
