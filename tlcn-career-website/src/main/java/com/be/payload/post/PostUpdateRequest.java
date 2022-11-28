package com.be.payload.post;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.be.utility.datatype.ESalary;
import com.be.utility.datatype.EStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateRequest {

	@NotNull
	private Long id;

	private String title;

	private String description;

	private Long salary;

	private ESalary salaryType;

	private String fieldCode;

	private String cityCode;

	private String location;

	@Min(0)
	private Long recruit;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	@Min(1)
	private Integer duration;
	
	private Long service;

	private EStatus status;
}
