package com.ekart.springekartapplication.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "customers")
@Data
public class Customer extends User {

	@Column(name = "address")
	private String address;

	@Column(name = "emailCustomer")
	private String emailCustomer;

	@Column(name = "phoneNumberCustomer")
	private Long phoneNumberCustomer;

}
