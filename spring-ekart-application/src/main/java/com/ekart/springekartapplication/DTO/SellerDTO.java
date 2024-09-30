package com.ekart.springekartapplication.DTO;

import lombok.Data;

@Data
public class SellerDTO extends UserDTO {

	private Long id;
	private String shopAddress;
	private String shopName;
	private String emailSeller;
	private String phoneNumberSeller;

}
