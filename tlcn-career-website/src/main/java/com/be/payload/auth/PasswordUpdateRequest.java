package com.be.payload.auth;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PasswordUpdateRequest {
	@NotEmpty
	@Size(min = 6, max = 50, message = "Password should have at least 6 characters")
	private String newPassword;
	
	@NotEmpty
	@Size(min = 6, max = 50, message = "Password should have at least 6 characters")
	private String oldPassword;
}
