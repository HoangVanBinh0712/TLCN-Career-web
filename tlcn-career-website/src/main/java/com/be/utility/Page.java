package com.be.utility;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Page implements Pageable {
	private int totalPage;
	private PageRequest pageRequest;
	
	public Page(Integer page, Integer size, int itemCount) {
		this(page, size, itemCount, Sort.unsorted());
	}
	
	public Page(Integer page, Integer size, int itemCount, Sort sort) {
		if (page == null) {
			page = 1;
		}
		if (size == null) {
			size = PlatformPolicy.DEFAULT_PAGE_SIZE;
		}
		
		pageRequest = PageRequest.of(page - 1, size, sort);
		int x =  (int)Math.ceil((double)itemCount/size);
		totalPage = x == 0 ? 1 : x;
	}

	@Override
	public int getPageNumber() {
		return pageRequest.getPageNumber();
	}

	@Override
	public int getPageSize() {
		return pageRequest.getPageSize();
	}

	@Override
	public long getOffset() {
		return pageRequest.getOffset();
	}

	@Override
	public Sort getSort() {
		return pageRequest.getSort();
	}

	@Override
	public Pageable next() {
		return pageRequest.next();
	}

	@Override
	public Pageable previousOrFirst() {
		return pageRequest.previousOrFirst();
	}

	@Override
	public Pageable first() {
		return pageRequest.first();
	}

	@Override
	public Pageable withPage(int pageNumber) {
		return pageRequest.withPage(pageNumber);
	}

	@Override
	public boolean hasPrevious() {
		return pageRequest.hasPrevious();
	}
}
