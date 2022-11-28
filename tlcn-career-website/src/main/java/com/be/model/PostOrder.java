package com.be.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.be.utility.datatype.ECurrency;
import com.be.utility.datatype.EOrderType;
import com.be.utility.datatype.EROrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "post_order")
@NoArgsConstructor
@AllArgsConstructor
public class PostOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8322776213884428425L;
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne
	@JoinColumn(name = "service_id")
	private Service service;

	// Month
	@Column(name = "duration")
	@NotNull
	@Min(1)
	private Integer duration;
	
	@Column(name = "price")
	@NotNull
	@Min(0)
	private Double price;

	// Duration * price
	@Column(name = "total")
	@NotNull
	@Min(0)
	private Double total;

	@Column(name = "currency", columnDefinition = "varchar(30)")
	@Enumerated(EnumType.STRING)
	@NotNull
	private ECurrency currency;

	@Column(name = "status", columnDefinition = "varchar(30)")
	@Enumerated(EnumType.STRING)
	@NotNull
	private EROrderStatus status;

	@Column(name = "type", columnDefinition = "varchar(30)")
	@Enumerated(EnumType.STRING)
	@NotNull
	private EOrderType type;

	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "payment_deadline")
	private Date paymentDeadline;
	
	@Column(name = "paid_date")
	private Date paidDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employer")
	private Employer employer;
}
