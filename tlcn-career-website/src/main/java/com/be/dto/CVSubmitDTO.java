package com.be.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CVSubmitDTO {

	// Url for the CV
	private String url;
	
	private Long matchPercent;
	
	private Date date;
}
