package com.ekart.springekartapplication.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class SellerDTO extends UserDTO {

	@JsonIgnore // Exclude from serialization
	private Long id;
	private String shopAddress;
	private String shopName;
	private String emailSeller;
	private String phoneNumberSeller;

}