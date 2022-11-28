package com.be.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.be.model.pk.CVSubmitPK;

import lombok.Data;

@Entity
@Data
public class CVSubmit {

	@EmbeddedId
	CVSubmitPK id;

	@MapsId("postId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", insertable = false, updatable = false)
	private Post post;
	
	@ManyToOne 
    @JoinColumn(name = "user_id",referencedColumnName = "user_id" , insertable = false, updatable = false)
    @JoinColumn(name = "media_id", referencedColumnName = "media_id", insertable = false, updatable = false)
    private Profile profile;
	
	@Column
	private Date date;
	
	@Column(name = "match_percent")
	private Long matchPercent;
}
