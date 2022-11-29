package com.be.model;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	private City city;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "avatar_id")
	private MediaResource avatar;

	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	@Email
	@Size(min = 7, max = 50, message = "Email must have between 7 and 50 chars.")
	private String email;

	@Column
	@Length(max = 120)
	@NotBlank
	private String password;

	@Column
	@Length(min = 5, max = 120, message = "Name must have between 5 and 120 chars.")
	private String name;

	@Column
	@Pattern(regexp = "0[0-9]+", message = "Phone number must contain only number character and begin with 0")
	@Size(min = 10, max = 10, message = "Phone number must have 10 number character")
	private String phone;

	@Column
	@NotBlank
	private String address;

	@Column(columnDefinition = "varchar(20)")
	private String code;

	@Column(name = "email_confirm")
	private Boolean emailConfirm;

	@Column
	private Boolean active;

}
