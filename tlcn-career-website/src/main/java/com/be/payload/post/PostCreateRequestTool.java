package com.be.payload.post;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.be.utility.datatype.ESalary;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequestTool {
    @NotBlank
	private String title;

	@NotBlank
	private String description;

	@Min(0)
	private Long salary;

	@NotNull
	private ESalary salaryType;

	@NotBlank
	private String fieldName;

	@NotNull
	private Long cityId;

	@NotBlank
	private String location;

	@Min(0)
	@NotNull
	private Long recruit;

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	// Month
	@NotNull
	@Min(1)
	private Integer duration;

	@NotNull
	private Long service;
    
}
