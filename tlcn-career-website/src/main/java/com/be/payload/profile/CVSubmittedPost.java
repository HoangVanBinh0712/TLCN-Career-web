package com.be.payload.profile;

import com.be.dto.CVSubmitDTO;
import com.be.dto.PostDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CVSubmittedPost {
	
	PostDTO post;
	
	CVSubmitDTO cvSubmit;

}
