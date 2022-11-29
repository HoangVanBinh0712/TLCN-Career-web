package com.be.service.impl;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.dto.list.PostsDTO;
import com.be.payload.ListWithPagingResponse;
import com.be.repository.PostRepository;
import com.be.service.PostSearchService;
import com.be.utility.Page;
import com.be.utility.datatype.ESalary;
import com.be.utility.datatype.EStatus;

@Service
public class PostSearchServiceImpl implements PostSearchService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Long getCountBeforSearch(String keyword, Long recruit, Long salary, ESalary eSalary, Long authorId,
            Long fieldId, Long cityId, EStatus status, Date expirationDate, Date startDate, Long serviceId) {
        return postRepository.adminCountBeforeSearch(keyword, recruit, salary, eSalary, authorId, fieldId, cityId,
                status, expirationDate, startDate, serviceId);
    }

    @Override
    public ListWithPagingResponse<PostsDTO> search(String keyword, Long recruit, Long salary, ESalary eSalary,
            Long authorId, Long fieldId, Long cityId, EStatus status, Date expirationDate, Date startDate,
            Long serviceId, Page page) {

        return new ListWithPagingResponse<>(page.getPageNumber() + 1, page.getTotalPage(), page.getPageSize(),
                postRepository
                        .adminSearch(keyword, recruit, salary, eSalary, authorId, fieldId, cityId, status,
                                expirationDate, startDate, serviceId, page)
                        .stream().map(p -> modelMapper.map(p, PostsDTO.class)).toList());
    }
}
