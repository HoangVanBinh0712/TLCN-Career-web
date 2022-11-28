package com.be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDTO extends BaseDTO {
	private String description;

	private Long employee;

	private FieldDTO field;

}
