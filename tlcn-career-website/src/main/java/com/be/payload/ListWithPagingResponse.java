package com.be.payload;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ListWithPagingResponse<T> extends BaseResponse {
	private int currentPage;
	private int totalPage;
	private int limit;
	private List<T> data;
	
	public ListWithPagingResponse(int currentPage, int totalPage,int limit, List<T> data) {
		super(true, "");
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.limit = limit;
		this.data = data;
	}
}
