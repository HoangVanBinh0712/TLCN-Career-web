package com.be.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CVSubmitPK implements Serializable {
	private static final long serialVersionUID = -478169959508107689L;

	@Column(name = "post_id")
	private Long postId;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "media_id")
	private Long mediaId;


}
