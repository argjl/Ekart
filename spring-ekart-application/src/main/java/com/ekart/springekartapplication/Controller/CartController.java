package com.ekart.springekartapplication.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ekart.springekartapplication.DTO.CartDTO;
import com.ekart.springekartapplication.DTO.CartResponseDTO;
import com.ekart.springekartapplication.DTO.ResponseDTO;
import com.ekart.springekartapplication.Entity.Customer;
import com.ekart.springekartapplication.Service.CartService;
import com.ekart.springekartapplication.Service.CustomerService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/customer/cart")
public class CartController {

	Logger logger = LoggerFactory.getLogger(CartController.class);

	@Autowired
	private CartService cartService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private Gson gson;

	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addProductToCart(@RequestParam Long productId, @RequestParam int quantity,
			Authentication authentication) {
		logger.info("CartController : addProductToCart Request Processing ");
		// Extract the username from the Authentication object
		String username = authentication.getName();
		// Fetch the Customer entity from the database using the username
		Customer customer = customerService.findByUsername(username);
		CartDTO createdCartDTO = cartService.addProductToCart(customer, productId, quantity);
		String jsonResponse = gson
				.toJson(new ResponseDTO<>("Product has been Updated or Added to the Cart Succesfully", createdCartDTO));
		logger.info("CartController : addProductToCart Response Processed ");

		return ResponseEntity.ok(jsonResponse);
	}

	@GetMapping(value = "/view", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CartResponseDTO> viewCart(Authentication authentication) {
		String username = authentication.getName(); // Extract username from token
		Customer customer = customerService.findByUsername(username); // Get customer details

		CartResponseDTO cartResponseDTO = new CartResponseDTO();
		cartResponseDTO.setId(customer.getId());
		cartResponseDTO.setUsername(username);
		cartResponseDTO.setItems(cartService.getItemsForCustomer(customer.getId()));

		return ResponseEntity.ok(cartResponseDTO);
	}

}
