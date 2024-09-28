package com.ekart.springekartapplication.Exception;

public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException(Long productId) {
		super("Product with ID " + productId + " not found.");
	}

	public ProductNotFoundException(Long productId, Long sellerId) {
		super("Product not found with id: " + productId + " for seller: " + sellerId);
	}

}
