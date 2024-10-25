package com.ekart.springekartapplication.Controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.springekartapplication.DTO.OrderDTO;
import com.ekart.springekartapplication.Entity.Customer;
import com.ekart.springekartapplication.Entity.Order;
import com.ekart.springekartapplication.Repository.CustomerRepository;
import com.ekart.springekartapplication.Service.CustomerService;
import com.ekart.springekartapplication.Service.OrderService;
import com.ekart.springekartapplication.Service.ProductService;
import com.ekart.springekartapplication.Service.SplunkLoggingService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private SplunkLoggingService splunkLoggingService;

	String requestMethod;

	@GetMapping("/orders")
	public ResponseEntity<?> getCustomerOrders(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Check if the user is authenticated and of the right type
		if (authentication != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();

			if (principal instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) principal;
				// Retrieve the authenticated customer by username
				Optional<Customer> customerOptional = customerRepository.findByUsername(userDetails.getUsername());
				Customer customer = customerOptional
						.orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

				// Get orders for the customer and return as OrderDTO list
				List<OrderDTO> orders = customerService.getOrders(customer);
				// Capture request method
				requestMethod = request.getMethod();
				// Set the request method in the request attributes for later use
				request.setAttribute("requestMethod", requestMethod);
				response.setStatus(HttpStatus.OK.value());
				if (orders.isEmpty()) {
					// If the order list is empty, return a specific message
					String responseBody = String.format("No orders for Customer with id:  %s", customer.getId());
					splunkLoggingService.logRequestAndResponse(request, response, responseBody);
					return ResponseEntity.ok("No orders for Customer with id: " + customer.getId());
				}
				String responseBody = String.format("Customer Controller :getCustomerOrders %s", orders);
				splunkLoggingService.logRequestAndResponse(request, response, responseBody);
				return ResponseEntity.ok(orders);
			}
		}

		requestMethod = request.getMethod();
		request.setAttribute("requestMethod", requestMethod);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		String responseBody = String.format("User not authenticated or not a seller.");
		splunkLoggingService.logRequestAndResponse(request, response, responseBody);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Return list of orders
	}

	// Endpoint to add a new order for a customer
	@PostMapping("/orders")
	public ResponseEntity<Order> addCustomerOrder(@RequestBody Order order) {
		Order savedOrder = customerService.saveOrder(order); // Saves the order to the repository
		return ResponseEntity.ok(savedOrder);
	}

}
