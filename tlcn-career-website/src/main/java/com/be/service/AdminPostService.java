package com.be.service;

import com.be.dto.PostDTO;
import com.be.payload.BaseResponse;
import com.be.payload.DataResponse;

import com.be.payload.statistic.StatisticResponse;

public interface AdminPostService {

	DataResponse<PostDTO> getPostDetail(Long postId);

	BaseResponse unAcceptPost(Long postId);

	BaseResponse acceptPost(String email, Long postId);

	DataResponse<StatisticResponse> getStatistic();

}
