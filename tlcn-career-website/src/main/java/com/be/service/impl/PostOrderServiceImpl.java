package com.be.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.dto.PostOrderDTO;
import com.be.model.Employer;
import com.be.payload.ListResponse;
import com.be.payload.ListWithPagingResponse;
import com.be.repository.EmployerRepository;
import com.be.repository.PostOrderRepository;
import com.be.service.PostOrderService;
import com.be.utility.Page;
import com.be.utility.datatype.EROrderStatus;

@Service
public class PostOrderServiceImpl implements PostOrderService {
    @Autowired
    private PostOrderRepository orderRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EmployerRepository empRepo;

    @Override
    public ListWithPagingResponse<PostOrderDTO> getOrders(EROrderStatus status, Long empId, Page page) {

        Employer emp = empRepo.findById(empId).get();
        return new ListWithPagingResponse<>(page.getPageNumber() + 1, page.getTotalPage(), page.getPageSize(),
                orderRepo.findByEmployerAndStatus(emp, status, page.getPageRequest()).stream()
                        .map(order -> mapper.map(order, PostOrderDTO.class)).toList());
    }

    @Override
    public Integer getCountOrder(EROrderStatus status, Long empId) {
        Employer emp = empRepo.findById(empId).get();

        return orderRepo.countByEmployerAndStatus(emp, status);
    }
}
