package com.ekart.springekartapplication.DTO;

import lombok.Data;

@Data
public class CartItemDTO {

	private Long productId;
	private String productName;
	private int quantity;
}
