package com.be.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.dto.CityDTO;
import com.be.model.City;
import com.be.payload.DataResponse;
import com.be.repository.CityRepository;

@Service
public class CityService {

    @Autowired
    CityRepository cityRepo;

    @Autowired
    ModelMapper mapper;

    public DataResponse<?> create(CityDTO request) {
        City city = mapper.map(request, City.class);
        return new DataResponse<>(true, "", mapper.map(cityRepo.save(city), CityDTO.class));
    }

    public DataResponse<?> update(CityDTO request) {
        City city = cityRepo.findById(request.getId()).get();
        city.setCode(request.getCode());
        city.setName(request.getName());
        return new DataResponse<>(true, "", mapper.map(cityRepo.save(city), CityDTO.class));
    }
}
