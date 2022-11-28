package com.be.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.controller.exception.CommonRuntimeException;
import com.be.dto.FieldDTO;
import com.be.model.Field;
import com.be.payload.DataResponse;
import com.be.payload.ListWithPagingResponse;
import com.be.repository.FieldRepository;
import com.be.service.FieldService;
import com.be.utility.Page;

@Service
public class FieldServiceImpl implements FieldService {
	@Autowired
	FieldRepository fieldRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public DataResponse<FieldDTO> create(FieldDTO request) {
		if (fieldRepository.existsByCode(request.getCode()))
			throw new CommonRuntimeException("Code is not available !");
		Field field = modelMapper.map(request, Field.class);

		return new DataResponse<>(true, "", modelMapper.map(fieldRepository.save(field), FieldDTO.class));
	}

	@Override
	public DataResponse<FieldDTO> update(FieldDTO request) {
		if (request.getId() == null)
			throw new CommonRuntimeException("Field not found !");

		Optional<Field> optField = fieldRepository.findById(request.getId());
		if (optField.isEmpty())
			throw new CommonRuntimeException("Field not found !");

		Field field = optField.get();
		if (!field.getCode().equals(request.getCode()) && fieldRepository.existsByCode(request.getCode()))
			throw new CommonRuntimeException("Code is not available !");

		field = modelMapper.map(request, Field.class);

		return new DataResponse<>(true, "", modelMapper.map(fieldRepository.save(field), FieldDTO.class));
	}

	@Override
	public int getCount(String key) {
		return fieldRepository.countBeforeSearch(key).intValue();
	}

	@Override
	public ListWithPagingResponse<FieldDTO> get(String key, Page page) {
		return new ListWithPagingResponse<>(page.getPageNumber() + 1, page.getTotalPage(), page.getPageSize(),
				fieldRepository.searchAndPaging(key, page).stream().map(f -> modelMapper.map(f, FieldDTO.class))
						.toList());
	}
}
