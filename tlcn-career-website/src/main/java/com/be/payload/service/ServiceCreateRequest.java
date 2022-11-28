package com.be.payload.service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.be.utility.datatype.ECurrency;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceCreateRequest {
	@NotBlank
	private String name;
	@NotBlank
	private String description;
	@NotNull
	private Integer cvSubmitAmount;
	@NotNull
	@Min(0)
	private Double price;
	@NotNull
	private ECurrency currency;

}
