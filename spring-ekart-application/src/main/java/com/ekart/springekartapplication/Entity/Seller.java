package com.ekart.springekartapplication.Entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

	@Column(name = "shopaddress")
	private String shopAddress;

	@Column(name = "shopname")
	private String shopName;

	@Column(name = "emailseller")
	private String emailSeller;

	@Column(name = "phonenumberseller")
	private String phoneNumberSeller;

	@OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
	@JsonBackReference
	private List<Product> products;

}
