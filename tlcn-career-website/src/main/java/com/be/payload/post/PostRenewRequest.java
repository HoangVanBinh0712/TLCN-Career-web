package com.be.payload.post;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRenewRequest {
	@NotNull
	private Long id;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	@NotNull
	@Min(1)
	private Integer duration;

	@NotNull
	private Long service;

}
