package com.ekart.springekartapplication.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sellers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Seller extends User {

	@Column(name="shopAddress")
	private String shopAddress;
	
	@Column(name="shopName")
	private String shopName;
	
	@Column(name="emailSeller")
	private String emailSeller;
	
	@Column(name="phoneNumberSeller")
	private String phoneNumberSeller;

}
