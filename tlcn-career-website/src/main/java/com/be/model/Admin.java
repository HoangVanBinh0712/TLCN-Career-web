package com.be.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.be.utility.datatype.EGender;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Admin extends BaseEntity {

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date birth;

	@Column
	private EGender gender;
}
