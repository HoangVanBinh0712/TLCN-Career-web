package com.be.service;

import org.springframework.web.multipart.MultipartFile;

import com.be.dto.EmployerDTO;
import com.be.payload.BaseResponse;
import com.be.payload.DataResponse;
import com.be.payload.auth.EmployerProfileCreateRequest;
import com.be.payload.auth.EmployerProfileUpdateRequest;
import com.be.payload.auth.PasswordUpdateRequest;
import com.be.payload.auth.ResetPasswordRequest;

public interface EmployerService {

	DataResponse<EmployerDTO> create(EmployerProfileCreateRequest request);

	DataResponse<EmployerDTO> update(Long employerId, EmployerProfileUpdateRequest request, MultipartFile avatar);

	EmployerDTO getEmployerProfile(String email);

	EmployerDTO getEmployerById(Long id);

	BaseResponse changePassword(String email, PasswordUpdateRequest request);

	BaseResponse emailConfirm(String email, String code);

	BaseResponse changePasswordWithResetCode(ResetPasswordRequest request);

	BaseResponse sendResetPasswordCode(String email);

}
