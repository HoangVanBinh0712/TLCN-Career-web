package com.be.service;

import com.be.utility.Page;
import com.be.utility.datatype.ESalary;
import com.be.utility.datatype.EStatus;
import com.be.payload.ListWithPagingResponse;

import java.util.Date;

import com.be.dto.list.PostsDTO;

public interface PostSearchService {
        ListWithPagingResponse<PostsDTO> search(String keyword, Long recruit, Long salary, ESalary eSalary,
                        Long authorId,
                        Long fieldId, Long cityId, EStatus status, Date expirationDate, Date startDate, Long serviceId,
                        Page page);

        Long getCountBeforSearch(String keyword, Long recruit, Long salary, ESalary eSalary, Long authorId,
                        Long fieldId, Long cityId, EStatus status, Date expirationDate, Date startDate, Long serviceId);

        ListWithPagingResponse<PostsDTO> getHotJob(int page, int limit);

}
