package com.be.payload;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ListResponse<T> extends BaseResponse {
	private List<T> data;

	public ListResponse(Boolean success, String message, List<T> data) {
		super(success, message);
		this.data = data;
	}

	public ListResponse(List<T> data) {
		super(true, "");
		this.data = data;
	}
}
