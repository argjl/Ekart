package com.ekart.springekartapplication.Exception;

public class SellerNotFoundException extends RuntimeException {

	public SellerNotFoundException(Long sellerId) {
		super("Seller with ID " + sellerId + " not found.");
	}

	public SellerNotFoundException(String message) {
		super(message);
	}
}
