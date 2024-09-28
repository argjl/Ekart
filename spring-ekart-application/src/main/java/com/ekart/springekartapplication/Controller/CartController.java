package com.ekart.springekartapplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.springekartapplication.Entity.Cart;
import com.ekart.springekartapplication.Entity.Customer;
import com.ekart.springekartapplication.Service.CartService;

@RestController
@RequestMapping("/customer/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/add")
	public ResponseEntity<Cart> addProductToCart(@RequestParam Long productId, @RequestParam int quantity,
			Authentication authentication) {
		Customer customer = (Customer) authentication.getPrincipal();
		return ResponseEntity.ok(cartService.addProductToCart(customer, productId, quantity));
	}

	@GetMapping
	public ResponseEntity<Cart> viewCart(Authentication authentication) {
		Customer customer = (Customer) authentication.getPrincipal();
		return ResponseEntity.ok(cartService.viewCart(customer));
	}

}
