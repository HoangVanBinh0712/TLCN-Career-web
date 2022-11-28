package com.be.dto;

import java.util.Date;

import com.be.utility.datatype.ECurrency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {

	private Long id;

	private String name;

	private String description;

	private Integer cvSubmitAmount;

	private Double price;
	
	private ECurrency currency;

	private Date createdDate;

	private Boolean active;
}
