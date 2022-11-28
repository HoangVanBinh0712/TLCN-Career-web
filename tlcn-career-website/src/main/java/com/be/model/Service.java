package com.be.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.be.utility.datatype.ECurrency;

import lombok.Data;

@Entity
@Table(name = "service")
@Data
public class Service {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotBlank
	private String name;

	@Column
	@NotBlank
	private String description;

	@Column(name = "cv_submit_amount")
	@NotNull
	@Min(0)
	private Integer cvSubmitAmount;
	
	@Column
	@NotNull
	@Min(0)
	private Double price;

	@Column(name = "currency", columnDefinition = "varchar(30)")
	@Enumerated(EnumType.STRING)
	@NotNull
	private ECurrency currency;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "active", columnDefinition = "boolean default true")
	private Boolean active;

}
