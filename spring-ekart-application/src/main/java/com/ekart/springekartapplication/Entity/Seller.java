package com.ekart.springekartapplication.Entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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

	@Column(name = "shop_Address")
	private String shopAddress;

	@Column(name = "shop_Name")
	private String shopName;

	@Column(name = "email_Seller")
	private String emailSeller;

	@Column(name = "phone_Number_Seller")
	private String phoneNumberSeller;

	@OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Product> products;

}
