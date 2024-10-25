package com.ekart.springekartapplication.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class OrderItemsDTO {
	private Long id;
	private ProductDTO product;

	// Exclude quantity from the response
	@JsonIgnore
	private int quantity;

}