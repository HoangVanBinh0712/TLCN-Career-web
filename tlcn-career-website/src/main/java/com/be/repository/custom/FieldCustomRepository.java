package com.be.repository.custom;

import java.util.List;

import com.be.model.Field;
import com.be.utility.Page;

public interface FieldCustomRepository {

	public Long countBeforeSearch(String key);
	
	public List<Field> searchAndPaging(String key, Page page);
}
