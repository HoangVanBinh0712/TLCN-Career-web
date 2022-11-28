package com.be.payload.predict;

import java.util.List;

import com.be.payload.BaseResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CVPredictResponse<T> extends BaseResponse {
	private int currentPage;
	private int totalPage;
	private List<T> data;
	private List<JobOptionResponse> jobOptionResponses;
	private String currentView;

	public CVPredictResponse(int currentPage, int totalPage, List<T> data, List<JobOptionResponse> jobOptionResponses,
			String currentView) {
		super(true, "");
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.data = data;
		this.jobOptionResponses = jobOptionResponses;
		this.currentView = currentView;
	}
}