package com.be.dto;

import java.util.Date;

import com.be.utility.datatype.EGender;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO extends BaseDTO {
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date birth;

	private EGender gender;
}
