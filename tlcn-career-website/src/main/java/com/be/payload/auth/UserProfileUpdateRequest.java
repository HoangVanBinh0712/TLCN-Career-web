package com.be.payload.auth;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.be.utility.datatype.EGender;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileUpdateRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7216410021155031071L;

	@NotNull
	private Long id;

	@Schema(type = "string", format = "email")
	@Email
	private String email;

	@Length(min = 5, max = 120, message = "Name must have between 5 and 120 chars.")
	private String name;

	@Pattern(regexp = "0[0-9]+", message = "Phone number must contain only number character and begin with 0")
	@Size(min = 10, max = 10, message = "Phone number must have 10 number character")
	private String phone;

	private String address;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birth;

	private EGender gender;

	private Long cityId;
}
