package com.be.service;

import com.be.dto.FieldDTO;
import com.be.payload.DataResponse;
import com.be.payload.ListWithPagingResponse;
import com.be.utility.Page;

public interface FieldService {

	ListWithPagingResponse<FieldDTO> get(String key, Page page);

	DataResponse<FieldDTO> update(FieldDTO request);

	DataResponse<FieldDTO> create(FieldDTO request);

	int getCount(String key);

}
