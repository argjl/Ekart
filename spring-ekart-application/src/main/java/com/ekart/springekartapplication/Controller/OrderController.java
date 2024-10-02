package com.ekart.springekartapplication.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.springekartapplication.DTO.OrderDTO;
import com.ekart.springekartapplication.Entity.Customer;
import com.ekart.springekartapplication.Entity.Order;
import com.ekart.springekartapplication.Service.CustomerService;
import com.ekart.springekartapplication.Service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	CustomerService customerService;

	@GetMapping("/vieworders")
	public List<OrderDTO> getOrdersByCustomer(Authentication authentication) {
		logger.info("OrderController : getOrdersByCustomer Request Processing ");
		String username = authentication.getName();
		Customer customer = customerService.findByUsername(username);
		logger.info("OrderController : getOrdersByCustomer Response Processed");
		return orderService.getOrdersByCustomer(customer);
	}

	@GetMapping("/{orderId}")
	public OrderDTO getOrderDetails(@PathVariable Long orderId) {
		logger.info("OrderController : getOrdersByCustomer Request Processing ");
		return orderService.getOrderById(orderId);
	}

}
