package com.ekart.springekartapplication.Entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "customers")
@Data
public class Customer extends User {

	private String address;
	private String emailCustomer;
	private Long phoneNumberCustomer;

}
