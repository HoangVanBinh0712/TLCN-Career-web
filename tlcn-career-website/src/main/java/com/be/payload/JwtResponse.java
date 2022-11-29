package com.be.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse<T> extends BaseResponse {
	private String token;
	private String refreshToken;
	// private static final String type = "Bearer";
	private T userInfo;

	public JwtResponse(String token, String refreshToken, T userInfo) {
		super(true, "");
		this.token = token;
		this.refreshToken = refreshToken;
		this.userInfo = userInfo;
	}
}