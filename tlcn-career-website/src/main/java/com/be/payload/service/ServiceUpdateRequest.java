package com.be.payload.service;

import javax.validation.constraints.NotNull;

import com.be.utility.datatype.ECurrency;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceUpdateRequest {

	@NotNull
	private Long id;
	private String name;
	private String description;
	private Integer cvSubmitAmount;
	private Double price;
	private ECurrency currency;
	private Boolean active;

}
