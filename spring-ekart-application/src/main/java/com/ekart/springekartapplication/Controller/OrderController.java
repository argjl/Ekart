package com.ekart.springekartapplication.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.springekartapplication.Entity.Customer;
import com.ekart.springekartapplication.Entity.Order;
import com.ekart.springekartapplication.Service.OrderService;

@RestController
@RequestMapping("/customer/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping
	public List<Order> getCUstomerOrders(Authentication authentication) {
		Customer customer = (Customer) authentication.getPrincipal();
		return orderService.getOrdersByCustomer(customer);
	}

	@GetMapping("/{orderId}")
	public Order getOrderDetails(@PathVariable Long orderId) {
		return orderService.getOrderById(orderId);
	}

}
