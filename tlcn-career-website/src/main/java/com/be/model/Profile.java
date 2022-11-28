package com.be.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.be.model.pk.ProfilePK;

import lombok.Data;

@Data
@Entity
public class Profile {
	@EmbeddedId
	ProfilePK id;

	@MapsId("userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	@MapsId("mediaId")
	@ManyToOne
	@JoinColumn(name = "media_id", insertable = false, updatable = false)
	private MediaResource mediaResource;

	@Column(name = "is_default",columnDefinition = "boolean default false")
	@NotNull
	private Boolean isDefault;
	
	@Column
	@NotEmpty
	private String name;
}
