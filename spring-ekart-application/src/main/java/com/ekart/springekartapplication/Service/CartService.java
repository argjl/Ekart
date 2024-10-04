package com.ekart.springekartapplication.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ekart.springekartapplication.Repository.CartItemRepository;
import com.ekart.springekartapplication.Repository.CartRepository;
import com.ekart.springekartapplication.Repository.ProductRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
	Logger logger = LoggerFactory.getLogger(CartService.class);

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartMapper cartMapper;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Transactional
	public CartDTO addProductToCart(Customer customer, Long productId, int quantity) {
		// Find the cart by customer ID
		logger.info("Entering the CartService:AddProduct to Cart Processing the Request from the Controller");
		Cart cart = cartRepository.findByCustomerId(customer.getId())
				.orElseThrow(() -> new CartNotFoundException(customer.getId()));

		// Fetch Product from DB
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException(productId));

		// Check if the product already exists in the cart
		Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);

		if (existingItem.isPresent()) {
			CartItem cartItem = existingItem.get();
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
			logger.info(cartItemRepository.save(cartItem).toString());
			cartItemRepository.save(cartItem);

		} else {
			CartItem newItem = new CartItem(cart, product, quantity);
			cartItemRepository.save(newItem);
		}

		return cartMapper.toDTO(cart);


	}

	public CartDTO viewCart(Customer customer) {
		Cart cart = cartRepository.findByCustomerId(customer.getId())
				.orElseThrow(() -> new CartNotFoundException(customer.getId()));

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

	public List<CartItemDTO> getItemsForCustomer(Long customerId) {
		Optional<Cart> optionalCart = cartRepository.findByCustomerId(customerId);
		if (optionalCart.isPresent()) {
			Cart cart = optionalCart.get();
			List<CartItemDTO> cartItemsDTO = new ArrayList<>();

			// Convert CartItem to CartItemDTO
			for (CartItem cartItem : cart.getItems()) {
				CartItemDTO cartItemDTO = new CartItemDTO();
				cartItemDTO.setProductId(cartItem.getProduct().getId());
				cartItemDTO.setProductName(cartItem.getProduct().getName());
				cartItemDTO.setQuantity(cartItem.getQuantity());
				cartItemsDTO.add(cartItemDTO);
			}

			return cartItemsDTO; // Return the populated list of CartItemDTO
		} else {
			return Collections.emptyList(); // Return an empty list if no cart is found
		}
	}
}
