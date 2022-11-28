package com.be.payload;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class BaseResponse {
	@NonNull
	private Boolean success;
	
	@NonNull
	private String message;

	public BaseResponse() {
		this.success = true;
		this.message = "";
	}
}
