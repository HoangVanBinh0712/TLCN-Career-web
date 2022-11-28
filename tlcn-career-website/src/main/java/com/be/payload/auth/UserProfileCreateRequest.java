package com.be.payload.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileCreateRequest {
	@NotBlank
	@Schema(type = "string", format = "email")
	@Email
	private String email;
	
	@NotEmpty
	@Size(min = 6, max = 30, message = "Password should have at least 6 characters")
	private String password;

	@NotEmpty
	@Length(min = 5, max = 120, message = "Name must have between 5 and 120 chars.")
	private String name;

	@Pattern(regexp = "0[0-9]+", message = "Phone number must contain only number character and begin with 0")
	@Size(min = 10, max = 10, message = "Phone number must have 10 number character")
	private String phone;

	@NotBlank
	private String address;
}
