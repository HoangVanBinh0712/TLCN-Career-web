package com.be.dto.list;

import java.util.Date;

import com.be.utility.datatype.ESalary;
import com.be.utility.datatype.EStatus;

import lombok.Data;

/*
 * Use when get a list of Post
 * We dont really need to map all attributes of Post -> PostDTO because it will query more sql to db
 * 
 * 
 */
@Data
public class PostsDTO {
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

	private Date acceptedDate;

	private EStatus status;

}
