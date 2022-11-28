package com.be.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.be.dto.ServiceDTO;
import com.be.model.Service;
import com.be.payload.DataResponse;
import com.be.payload.ListResponse;
import com.be.payload.service.ServiceCreateRequest;
import com.be.payload.service.ServiceUpdateRequest;
import com.be.repository.ServiceRepository;
import com.be.service.PackageService;

@org.springframework.stereotype.Service
public class PackageServiceImpl implements PackageService {

	@Autowired
	ServiceRepository serviceRepository;

	@Autowired
	ModelMapper mapper;

	@Override
	public DataResponse<ServiceDTO> create(ServiceCreateRequest request) {
		try {
			Service sv = mapper.map(request, Service.class);
			sv.setActive(true);
			sv.setCreatedDate(new Date());
			sv = serviceRepository.save(sv);

			return new DataResponse<>(true, "Service create successfully.", mapper.map(sv, ServiceDTO.class));

		} catch (Exception ex) {
			return new DataResponse<>(false, "Something went wrong, please try again.", null);
		}
	}

	@Override
	public DataResponse<ServiceDTO> update(ServiceUpdateRequest request) {
		Optional<Service> opt = serviceRepository.findById(request.getId());
		if (opt.isEmpty())
			return new DataResponse<>(false, "Service not found with id: " + request.getId(), null);
		Service sv = opt.get();
		if (!StringUtils.isBlank(request.getName()))
			sv.setName(request.getName());
		if (!StringUtils.isBlank(request.getDescription()))
			sv.setDescription(request.getDescription());
		if (request.getCvSubmitAmount() != null)
			sv.setCvSubmitAmount(request.getCvSubmitAmount());
		if (request.getPrice() != null)
			sv.setPrice(request.getPrice());
		if (request.getCurrency() != null)
			sv.setCurrency(request.getCurrency());
		if (request.getActive() != null)
			sv.setActive(request.getActive());

		sv = serviceRepository.save(sv);

		return new DataResponse<>(true, "Update service successfully !", mapper.map(sv, ServiceDTO.class));

	}

	@Override
	public ListResponse<ServiceDTO> getListService() {
		List<ServiceDTO> result = serviceRepository.findAll().stream().map(sv -> mapper.map(sv, ServiceDTO.class))
				.toList();
		if (result.isEmpty())
			return new ListResponse<>(false, "No service found.", result);
		return new ListResponse<>(result);
	}

	@Override
	public ListResponse<ServiceDTO> getListService(Boolean active) {
		List<ServiceDTO> result = serviceRepository.findByActive(active).stream()
				.map(sv -> mapper.map(sv, ServiceDTO.class)).toList();
		if (result.isEmpty())
			return new ListResponse<>(false, "No service found.", result);
		return new ListResponse<>(result);
	}

}
