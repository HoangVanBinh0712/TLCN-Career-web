package com.be.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.be.dto.ProfileDTO;
import com.be.dto.UserDTO;
import com.be.payload.BaseResponse;
import com.be.payload.DataResponse;
import com.be.payload.ListWithPagingResponse;
import com.be.payload.auth.PasswordUpdateRequest;
import com.be.payload.auth.ResetPasswordRequest;
import com.be.payload.auth.UserProfileCreateRequest;
import com.be.payload.auth.UserProfileUpdateRequest;
import com.be.payload.profile.CVSubmittedPost;

public interface UserService {

	DataResponse<UserDTO> create(UserProfileCreateRequest request);

	DataResponse<UserDTO> update(Long userId,UserProfileUpdateRequest request, MultipartFile avatar);

	UserDTO getUserProfile(String email);

	BaseResponse uploadCV(String email, MultipartFile cv, Boolean isDefault, String name);

	List<ProfileDTO> getListCV(Long userId);

	ProfileDTO getCVDetail(Long userId, Long mediaId);

	BaseResponse deleteCV(Long userId, Long mediaId);

	BaseResponse changePassword(String email, PasswordUpdateRequest request);

	ListWithPagingResponse<CVSubmittedPost> getCVSubmittedPost(Long userId, Integer page, Integer limit);

	BaseResponse emailConfirm(String email, String code);

	BaseResponse changePasswordWithResetCode(ResetPasswordRequest request);

	BaseResponse sendResetPasswordCode(String email);
}
