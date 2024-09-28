package com.ekart.springekartapplication.Service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import com.ekart.springekartapplication.Entity.Cart;
import com.ekart.springekartapplication.Entity.CartItem;
import com.ekart.springekartapplication.Entity.Customer;
import com.ekart.springekartapplication.Entity.Product;
import com.ekart.springekartapplication.Exception.CartNotFoundException;
import com.ekart.springekartapplication.Exception.ProductNotFoundException;
import com.ekart.springekartapplication.Repository.CartRepository;
import com.ekart.springekartapplication.Repository.ProductRepository;

import java.util.Optional;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	public Cart addProductToCart(Customer customer, Long productId, int quantity) {
		// Find the product by ID
		Optional<Product> productOptional = productRepository.findById(productId);
		if (!productOptional.isPresent()) {
			throw new ProductNotFoundException(productId);
		}

		// Check if the customer has an existing cart
		Cart cart = cartRepository.findByCustomerId(customer.getId()).orElse(new Cart()); // If no cart exists, create a
																							// new one
		cart.setCustomer(customer);

		// Check if the product already exists in the cart
		boolean productExistsInCart = false;
		for (CartItem item : cart.getItems()) {
			if (item.getProduct().getId().equals(productId)) {
				item.setQuantity(item.getQuantity() + quantity); // Update the quantity
				productExistsInCart = true;
				break;
			}
		}
		// If Product is not in the cart, add it
		if (!productExistsInCart) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(productOptional.get());
			cartItem.setQuantity(quantity);
			cartItem.setCart(cart);
			cart.getItems().add(cartItem);
		}

		// Save the Updated Cart
		return cartRepository.save(cart);
	}

	public Cart viewCart(Customer customer) {
		return cartRepository.findByCustomerId(customer.getId())
				.orElseThrow(() -> new CartNotFoundException(customer.getId()));
	}
}
