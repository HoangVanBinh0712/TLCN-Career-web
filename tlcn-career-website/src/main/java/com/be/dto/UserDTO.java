package com.be.dto;

import java.util.Date;
import java.util.List;

import com.be.utility.datatype.EGender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends BaseDTO {
	private Date birth;

	private EGender gender;

	private List<ProfileDTO> profiles;
}
