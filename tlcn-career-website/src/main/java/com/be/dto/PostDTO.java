package com.be.dto;

import java.util.Date;

import com.be.utility.datatype.ESalary;
import com.be.utility.datatype.EStatus;

import lombok.Data;

@Data
public class PostDTO {

	private Long id;

	private String title;

	private String description;

	private Long salary;

	private ESalary salaryType;

	private String location;

	private Long recruit;

	private Date createDate;

	private Date startDate;

	private Date expirationDate;

	private String adminAceptedEmail;

	private Date acceptedDate;

	private EmployerDTO employer;

	private String city;

	private String field;

	private String avatar;

	private EStatus status;

	private ServiceDTO service;

}
