package com.be.payload.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
	@NotBlank
	@Email
	private String email;

	@NotBlank
	@Length(min = 8)
	private String newPassword;

	@NotBlank
	private String code;
}
