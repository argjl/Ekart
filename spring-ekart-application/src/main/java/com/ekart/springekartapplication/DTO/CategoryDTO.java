package com.ekart.springekartapplication.DTO;

import javax.validation.constraints.NotEmpty;

import lombok.Data;


@Data
public class CategoryDTO {
	private Long id;
	
	@NotEmpty (message = "Category name must not be null or empty.")
	private String categoryName;
}
