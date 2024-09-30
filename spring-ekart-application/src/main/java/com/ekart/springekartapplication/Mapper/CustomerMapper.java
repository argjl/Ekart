package com.ekart.springekartapplication.Mapper;

import com.ekart.springekartapplication.DTO.CustomerDTO;
import com.ekart.springekartapplication.Entity.Customer;

public class CustomerMapper extends UserMapper {
	public static CustomerDTO toDTO(Customer customer) {
		CustomerDTO dto = new CustomerDTO();
		dto.setId(customer.getId());
		dto.setUsername(customer.getUsername());
		dto.setRole(customer.getRole());
		dto.setAddress(customer.getAddress());
		dto.setPhoneNumberCustomer(customer.getPhoneNumberCustomer());
		dto.setEmailCustomer(customer.getEmailCustomer());
		return dto;
	}

	public static Customer toEntity(CustomerDTO dto) {
		Customer customer = new Customer();
		customer.setId(dto.getId());
		customer.setUsername(dto.getUsername());
		customer.setRole(dto.getRole());
		customer.setAddress(dto.getAddress());
		customer.setPhoneNumberCustomer(dto.getPhoneNumberCustomer());
		customer.setEmailCustomer(dto.getEmailCustomer());
		return customer;
	}
}
