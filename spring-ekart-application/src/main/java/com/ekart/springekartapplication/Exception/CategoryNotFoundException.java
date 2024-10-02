package com.ekart.springekartapplication.Exception;

public class CategoryNotFoundException extends RuntimeException {
	public CategoryNotFoundException(Long categoryId) {
		super("Cart not found for customer with id: " + categoryId);
}
}
