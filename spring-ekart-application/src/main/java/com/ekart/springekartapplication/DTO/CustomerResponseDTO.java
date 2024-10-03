package com.ekart.springekartapplication.DTO;

import lombok.Data;

@Data
public class CustomerResponseDTO {

	private String username;
	private String emailCustomer;
	private Long phoneNumberCustomer;
	private String address;

}
