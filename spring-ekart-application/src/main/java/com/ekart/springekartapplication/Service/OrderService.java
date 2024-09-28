package com.ekart.springekartapplication.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ekart.springekartapplication.Entity.Customer;
import com.ekart.springekartapplication.Entity.Order;
import com.ekart.springekartapplication.Entity.Seller;
import com.ekart.springekartapplication.Exception.OrderNotFoundException;
import com.ekart.springekartapplication.Repository.OrderRespository;

@Service
public class OrderService {

	@Autowired
	private OrderRespository orderRepository;
	
	public List<Order> getOrdersByCustomer(Customer customer) {
		return orderRepository.findByCustomerId(customer.getId());
	}

	public Order getOrderById(Long orderId) {
		return orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException(orderId));
		
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
