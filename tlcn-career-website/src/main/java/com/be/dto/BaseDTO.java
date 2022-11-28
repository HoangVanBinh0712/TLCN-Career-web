package com.be.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public abstract class BaseDTO {

	private CityDTO city;

	private String avatar;

	private String id;
	
	@Email
	@Size(min = 7, max = 50, message = "Email must have between 7 and 50 chars.")
	private String email;

	@Length(min = 5, max = 120, message = "Name must have between 5 and 120 chars.")
	private String name;

	@Pattern(regexp = "0[0-9]+", message = "Phone number must contain only number character and begin with 0")
	@Size(min = 10, max = 10, message = "Phone number must have 10 number character")
	private String phone;

	@NotBlank
	private String address;

}
