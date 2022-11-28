package com.be.repository.custom;

import java.util.Date;
import java.util.List;

import com.be.model.Post;
import com.be.utility.Page;
import com.be.utility.datatype.ESalary;
import com.be.utility.datatype.EStatus;

public interface PostSearchCustomRepository {
	public List<Post> adminSearch(String keyword, Long recruit, Long salary, ESalary eSalary, Long authorId,
			Long fieldId, Long cityId, EStatus status, Date expirationDate, Date startDate, Long serviceId, Page page);

	public Long adminCountBeforeSearch(String keyword, Long recruit, Long salary, ESalary eSalary, Long authorId,
			Long fieldId, Long cityId, EStatus status, Date expirationDate, Date startDate, Long serviceId);
}
