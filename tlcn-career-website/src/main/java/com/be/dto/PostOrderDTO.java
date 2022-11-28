package com.be.dto;

import java.util.Date;

import com.be.utility.datatype.ECurrency;
import com.be.utility.datatype.EOrderType;
import com.be.utility.datatype.EROrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostOrderDTO {
	private Long id;

	private PostDTO post;

	private ServiceDTO service;

	private Integer duration;

	// Duration * service.price
	private Double total;

	private ECurrency currency;

	private EROrderStatus status;

	private EOrderType type;

	private Date createdDate;

	private Date paymentDeadline;

	private Date paidDate;
}
