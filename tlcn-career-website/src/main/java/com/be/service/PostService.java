package com.be.service;

import org.springframework.web.multipart.MultipartFile;

import com.be.dto.PostDTO;
import com.be.payload.BaseResponse;
import com.be.payload.DataResponse;
import com.be.payload.post.PostCreateRequest;
import com.be.payload.post.PostCreateRequestTool;
import com.be.payload.post.PostRenewRequest;
import com.be.payload.post.PostUpdateRequest;

public interface PostService {

	DataResponse<PostDTO> getDetail(Long postId);

	DataResponse<PostDTO> update(Long empId, PostUpdateRequest request, MultipartFile avatar);

	DataResponse<PostDTO> create(Long empId, PostCreateRequest request, MultipartFile avatar);

	DataResponse<PostDTO> getEmployerPostDetail(Long empId, Long postId);

	BaseResponse deletePost(Long empId, Long postId);

	DataResponse<PostDTO> renew(Long empId, PostRenewRequest request);

	DataResponse<PostDTO> createTool(Long empId, PostCreateRequestTool request, MultipartFile avatar);

}
