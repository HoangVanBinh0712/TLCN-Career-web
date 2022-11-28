package com.be.service;

import com.be.dto.CVSubmitDTO;
import com.be.payload.BaseResponse;
import com.be.payload.ListWithPagingResponse;

public interface CVSubmitService {

	BaseResponse sumbitCV(Long userId, Long postId, Long mediaId);

	BaseResponse deleteSumbittedCV(Long userId, Long postId, Long mediaId);

	ListWithPagingResponse<CVSubmitDTO> getListCV(Long employerId, Long postId, Integer page, Integer limit);

}
