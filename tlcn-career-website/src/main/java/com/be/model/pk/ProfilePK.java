package com.be.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProfilePK implements Serializable {

	private static final long serialVersionUID = -1368353305031820607L;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "media_id")
	private Long mediaId;

}
