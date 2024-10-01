package com.ekart.springekartapplication.Service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ekart.springekartapplication.DTO.CartDTO;
import com.ekart.springekartapplication.DTO.CartItemDTO;
import com.ekart.springekartapplication.Entity.Cart;
import com.ekart.springekartapplication.Entity.CartItem;
import com.ekart.springekartapplication.Entity.Customer;
import com.ekart.springekartapplication.Entity.Product;
import com.ekart.springekartapplication.Exception.CartNotFoundException;
import com.ekart.springekartapplication.Exception.ProductNotFoundException;
import com.ekart.springekartapplication.Mapper.CartMapper;
import com.ekart.springekartapplication.Repository.CartRepository;
import com.ekart.springekartapplication.Repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartMapper cartMapper;

	public CartDTO addProductToCart(Customer customer, Long productId, int quantity) {
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

		for (CartItem item : cart.getItems()) {
			if (item.getProduct().getId().equals(productId)) {
				item.setQuantity(item.getQuantity() + quantity); // Update the quantity

				return cartMapper.toDTO(cartRepository.save(cart));
			}
		}
		
		// If Product is not in the cart, add it
		CartItem cartItem = new CartItem();
			cartItem.setProduct(productOptional.get());
			cartItem.setQuantity(quantity);
			cartItem.setCart(cart);
			cart.getItems().add(cartItem);
		

		// Save the Updated Cart
		return cartMapper.toDTO(cartRepository.save(cart));
	}

	public CartDTO viewCart(Customer customer) {
		Cart cart = cartRepository.findByCustomerId(customer.getId()).orElseThrow(()->new CartNotFoundException(customer.getId()));
		 
		 CartDTO cartDTO = new CartDTO();
		 cartDTO.setId(cart.getId());
		 List<CartItemDTO> itemDTOs = new ArrayList<>();
		 
		for (CartItem item : cart.getItems()) {
	        CartItemDTO itemDTO = new CartItemDTO();
	        itemDTO.setProductId(item.getProduct().getId());
	        itemDTO.setProductName(item.getProduct().getName());
	        itemDTO.setQuantity(item.getQuantity());
	        itemDTOs.add(itemDTO);
	    }
	    cartDTO.setItems(itemDTOs);

	    return cartDTO;
		
	}
}
