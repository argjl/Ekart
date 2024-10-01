package com.ekart.springekartapplication.DTO;

import java.util.List;

import lombok.Data;

@Data
public class CartDTO {

	private Long id;
	private List<CartItemDTO> items;
	private Long customerId;
}
