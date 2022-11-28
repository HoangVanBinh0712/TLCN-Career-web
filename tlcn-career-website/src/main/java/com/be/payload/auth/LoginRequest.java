package com.be.payload.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {
	@NotBlank
	private String username;
	@NotBlank
	@Size(min = 6)
	private String password;
}
