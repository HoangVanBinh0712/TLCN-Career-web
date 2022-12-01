package com.be.service.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.be.dto.list.PostsDTO;
import com.be.payload.ListWithPagingResponse;
import com.be.repository.PostRepository;
import com.be.repository.ServiceRepository;
import com.be.service.PostSearchService;
import com.be.utility.Page;
import com.be.utility.datatype.ESalary;
import com.be.utility.datatype.EStatus;

@Service
public class PostSearchServiceImpl implements PostSearchService {
        @Autowired
        PostRepository postRepository;

        @Autowired
        ServiceRepository serviceRepo;

        @Autowired
        ModelMapper modelMapper;

        @Override
        public Long getCountBeforSearch(String keyword, Long recruit, Long salary, ESalary eSalary, Long authorId,
                        Long fieldId, Long cityId, EStatus status, Date expirationDate, Date startDate,
                        Long serviceId) {
                return postRepository.adminCountBeforeSearch(keyword, recruit, salary, eSalary, authorId, fieldId,
                                cityId,
                                status, expirationDate, startDate, serviceId);
        }

        @Override
        public ListWithPagingResponse<PostsDTO> search(String keyword, Long recruit, Long salary, ESalary eSalary,
                        Long authorId, Long fieldId, Long cityId, EStatus status, Date expirationDate, Date startDate,
                        Long serviceId, Page page) {

                return new ListWithPagingResponse<>(page.getPageNumber() + 1, page.getTotalPage(), page.getPageSize(),
                                postRepository
                                                .adminSearch(keyword, recruit, salary, eSalary, authorId, fieldId,
                                                                cityId, status,
                                                                expirationDate, startDate, serviceId, page)
                                                .stream().map(p -> modelMapper.map(p, PostsDTO.class)).toList());
        }

        public ListWithPagingResponse<PostsDTO> getHotJob(int page, int limit) {
                // Get premium service
                List<Long> lst = serviceRepo.findByName("PREMIUM");
                return new ListWithPagingResponse<>(page + 1 + 1, 1, limit,
                                postRepository.getJobByArrService(lst, PageRequest.of(page, limit))
                                                .stream().map(p -> modelMapper.map(p, PostsDTO.class)).toList());

        }
}
