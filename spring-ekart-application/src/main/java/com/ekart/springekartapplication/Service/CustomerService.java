package com.ekart.springekartapplication.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ekart.springekartapplication.DTO.OrderDTO;
import com.ekart.springekartapplication.DTO.OrderItemsDTO;
import com.ekart.springekartapplication.DTO.ProductDTO;
import com.ekart.springekartapplication.DTO.SellerDTO;
import com.ekart.springekartapplication.Entity.Customer;
import com.ekart.springekartapplication.Entity.Order;
import com.ekart.springekartapplication.Entity.OrderItems;
import com.ekart.springekartapplication.Entity.Product;
import com.ekart.springekartapplication.Mapper.CategoryMapper;
import com.ekart.springekartapplication.Mapper.OrderItemsMapper;
import com.ekart.springekartapplication.Repository.CustomerRepository;
import com.ekart.springekartapplication.Repository.OrderRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private OrderItemsMapper orderItemsMapper;

//	public List<OrderDTO> getOrders(Customer customer) {
//		List<OrderDTO> orders = orderService.getOrdersByCustomer(customer);
//		return orders.stream().collect(Collectors.toList());
//	}

	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}

	public Customer findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<OrderDTO> getOrders(Customer customer) {
		// Retrieve and convert orders for the customer
		List<Order> orders = orderRepository.findByCustomerId(customer.getId());
		return orders.stream().map(order -> convertToOrderDTO(order)).collect(Collectors.toList());
	}

	private OrderDTO convertToOrderDTO(Order order) {
		// Convert and populate the OrderDTO as per requirements
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setOrderDate(order.getOrderDate());
		orderDTO.setPrice(order.getPrice());
		orderDTO.setOrderItems(convertOrderItems(order.getOrderItems()));

		return orderDTO;
	}

	private List<OrderItemsDTO> convertOrderItems(List<OrderItems> orderItems) {
		// Map OrderItems to OrderItemDTO while excluding quantity
		return orderItems.stream().map(item -> {
			OrderItemsDTO itemDTO = new OrderItemsDTO();
			itemDTO.setId(item.getId());
			itemDTO.setProduct(convertToProductDTO(item.getProduct()));
			return itemDTO;
		}).collect(Collectors.toList());
	}

	private ProductDTO convertToProductDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setDescription(product.getDescription());
		productDTO.setPrice(product.getPrice());

		// Set SellerDTO while excluding sensitive data
		SellerDTO sellerDTO = new SellerDTO();
		sellerDTO.setShopAddress(product.getSeller().getShopAddress());
		sellerDTO.setShopName(product.getSeller().getShopName());
		sellerDTO.setEmailSeller(product.getSeller().getEmailSeller());
		sellerDTO.setPhoneNumberSeller(product.getSeller().getPhoneNumberSeller());

		productDTO.setSeller(sellerDTO);
		productDTO.setCategory(categoryMapper.toDTO(product.getCategory()));

		return productDTO;
	}
}
