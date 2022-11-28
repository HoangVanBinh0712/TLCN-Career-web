package com.be.dto;

import lombok.Data;

@Data
public class ProfileDTO {

	private Long mediaId;
	
	private String url;
	
	private Boolean isDefault;
	
	private String name;
}
