package com.ekart.springekartapplication.DTO;

import lombok.Data;

@Data
public class CustomerDTO extends UserDTO {
	private String address;
	private Long phoneNumberCustomer;
	private String emailCustomer;
}
