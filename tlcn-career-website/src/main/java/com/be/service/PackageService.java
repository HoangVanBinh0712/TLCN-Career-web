package com.be.service;

import com.be.dto.ServiceDTO;
import com.be.payload.DataResponse;
import com.be.payload.ListResponse;
import com.be.payload.service.ServiceCreateRequest;
import com.be.payload.service.ServiceUpdateRequest;

public interface PackageService {

	ListResponse<ServiceDTO> getListService();

	DataResponse<ServiceDTO> update(ServiceUpdateRequest request);

	DataResponse<ServiceDTO> create(ServiceCreateRequest request);

	ListResponse<ServiceDTO> getListService(Boolean active);

}
