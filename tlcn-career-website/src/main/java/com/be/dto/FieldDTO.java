package com.be.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class FieldDTO {

	private Long id;
	
	@NotBlank
	@Length(min = 3)
	private String name;
	
	@NotBlank
	@Length(min = 3)
	private String code;
}
