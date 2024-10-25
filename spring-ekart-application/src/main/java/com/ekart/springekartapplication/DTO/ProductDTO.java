package com.ekart.springekartapplication.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class ProductDTO {
	private Long id;
	private String name;
	private String description;
	private Long price;
	private int quantity;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private SellerDTO seller;
	private CategoryDTO category;
}
