package com.be.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.be.dto.PostDTO;
import com.be.payload.BaseResponse;
import com.be.payload.DataResponse;
import com.be.payload.ListWithPagingResponse;
import com.be.payload.statistic.CountPaidPostByYear;
import com.be.payload.statistic.StatisticResponse;
import com.be.utility.Page;
import com.be.utility.datatype.ESalary;
import com.be.utility.datatype.EStatus;

public interface AdminPostService {

	ListWithPagingResponse<PostDTO> search(String keyword, Long recruit, Long salary, ESalary eSalary, Long authorId,
			Long fieldId, Long cityId, EStatus status, Date expirationDate, Date startDate, Long serviceId, Page page);

	DataResponse<PostDTO> getPostDetail(Long postId);

	BaseResponse unAcceptPost(Long postId);

	BaseResponse acceptPost(String email, Long postId);

	Long getCountBeforSearch(String keyword, Long recruit, Long salary, ESalary eSalary, Long authorId,
			Long fieldId, Long cityId, EStatus status, Date expirationDate, Date startDate, Long serviceId);

	DataResponse<StatisticResponse> getStatistic();

}
