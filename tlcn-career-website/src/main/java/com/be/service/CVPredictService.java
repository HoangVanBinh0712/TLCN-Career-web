package com.be.service;

import com.be.dto.PostDTO;
import com.be.payload.ListWithPagingResponse;
import com.be.payload.predict.CVPredictResponse;

public interface CVPredictService {

	CVPredictResponse<PostDTO> predict(Long mediaId, Long userId);

	ListWithPagingResponse<PostDTO> getPostByField(String code, Integer pageNumber, Integer limit);

}
