package com.ekart.springekartapplication.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.springekartapplication.DTO.CartDTO;
import com.ekart.springekartapplication.Entity.Cart;
import com.ekart.springekartapplication.Entity.Customer;
import com.ekart.springekartapplication.Service.CartService;
import com.ekart.springekartapplication.Service.CustomerService;

@RestController
@RequestMapping("/customer/cart")
public class CartController {

	Logger logger = LoggerFactory.getLogger(CartController.class);

	@Autowired
	private CartService cartService;

	@Autowired
	private CustomerService customerService;

	@PostMapping("/add")
	public ResponseEntity<CartDTO> addProductToCart(@RequestParam Long productId, @RequestParam int quantity,
			Authentication authentication) {
		logger.info("CartController : addProductToCart Request Processing ");
		// Extract the username from the Authentication object
		String username = authentication.getName();
		// Fetch the Customer entity from the database using the username
		Customer customer = customerService.findByUsername(username);
		logger.info("CartController : addProductToCart Response Processing ");
		return ResponseEntity.ok(cartService.addProductToCart(customer, productId, quantity));
	}

	@GetMapping("/view")
	public ResponseEntity<CartDTO> viewCart(Authentication authentication) {
		logger.info("CartController : viewCart Request Processing");
		// Extract the username from the Authentication object
		String username = authentication.getName();
		// Fetch the Customer entity from the database using the username
		Customer customer = customerService.findByUsername(username);
		logger.info("CartController : viewCart Response Processing");
		return ResponseEntity.ok(cartService.viewCart(customer));
	}

}
