package com.be.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Entity
public class Field {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotBlank
	@Length(min = 3)
	private String name;

	@Column(unique = true, columnDefinition = "varchar(30)")
	@NotBlank
	@Length(min = 3)
	private String code;

	@OneToMany(mappedBy = "field")
	private List<Employer> employers;

	@OneToMany(mappedBy = "field")
	private List<Post> post;
}
