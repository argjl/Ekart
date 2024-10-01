package com.ekart.springekartapplication.DTO;

import java.util.List;



import lombok.Data;

@Data
public class CartResponse {

	private Long id;
	private String username;
	private List<CartItemDTO> items;

}
