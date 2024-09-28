package com.ekart.springekartapplication.Exception;

public class CartNotFoundException extends RuntimeException{
	public CartNotFoundException(Long customerId) {
		super("Cart not found for customer with id: " + customerId);
	}

}
