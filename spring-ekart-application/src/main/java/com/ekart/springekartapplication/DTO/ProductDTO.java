package com.ekart.springekartapplication.DTO;

import lombok.Data;

@Data
public class ProductDTO {
	private Long id;
	private String name;
	private String description;
	private Long price;
	private int quantity;
	private SellerDTO seller;
	private CategoryDTO category;
}
