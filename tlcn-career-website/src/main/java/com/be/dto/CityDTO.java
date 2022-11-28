package com.be.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CityDTO {
	private Long id;

	@NotBlank
	private String name;
	
	@NotBlank
	private String code;
}
