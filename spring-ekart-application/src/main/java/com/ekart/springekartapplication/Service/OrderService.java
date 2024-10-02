package com.ekart.springekartapplication.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ekart.springekartapplication.DTO.OrderDTO;
import com.ekart.springekartapplication.Entity.Customer;
import com.ekart.springekartapplication.Entity.Order;
import com.ekart.springekartapplication.Entity.Seller;
import com.ekart.springekartapplication.Exception.OrderNotFoundException;
import com.ekart.springekartapplication.Repository.OrderRespository;

@Service
public class OrderService {

	@Autowired
	private OrderRespository orderRepository;

	public List<OrderDTO> getOrdersByCustomer(Customer customer) {
		List<Order> orders = orderRepository.findByCustomerId(customer.getId());
		return orders.stream().map(order -> {
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setId(order.getId());
			orderDTO.setOrderDate(order.getOrderDate());
			orderDTO.setPrice(order.getPrice());
			orderDTO.setSeller(order.getSeller());
			orderDTO.setOrderItems(order.getOrderItems());
			return orderDTO;
		}).collect(Collectors.toList());
	}

	public OrderDTO getOrderById(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));

		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setOrderDate(order.getOrderDate());
		orderDTO.setPrice(order.getPrice());
		orderDTO.setSeller(order.getSeller()); // Set seller details
		orderDTO.setOrderItems(order.getOrderItems());

		return orderDTO;

	}

	public List<Order> getOrdersBySeller(Seller seller) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Order> getTotalSales(Seller seller) {
		// TODO Auto-generated method stub
		return null;
	}

}
