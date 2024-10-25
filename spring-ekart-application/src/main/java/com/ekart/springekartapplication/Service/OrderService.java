package com.ekart.springekartapplication.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ekart.springekartapplication.DTO.OrderDTO;
import com.ekart.springekartapplication.DTO.SellerDTO;
import com.ekart.springekartapplication.Entity.Customer;
import com.ekart.springekartapplication.Entity.Order;
import com.ekart.springekartapplication.Entity.Seller;
import com.ekart.springekartapplication.Exception.OrderNotFoundException;
import com.ekart.springekartapplication.Mapper.OrderItemsMapper;
import com.ekart.springekartapplication.Mapper.SellerMapper;
import com.ekart.springekartapplication.Repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemsMapper orderItemsMapper;

	@Autowired
	private SellerMapper sellerMapper;

	public List<OrderDTO> getOrdersByCustomer(Customer customer) {
		List<Order> orders = orderRepository.findByCustomerId(customer.getId());
		return orders.stream().map(order -> {
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setId(order.getId());
			orderDTO.setOrderDate(order.getOrderDate());
			orderDTO.setPrice(order.getPrice());

			// Avoid setting seller if it's null
			if (order.getSeller() != null) {
				Seller seller = order.getSeller();
				SellerDTO sellerDTO = new SellerDTO();
				sellerDTO.setShopAddress(seller.getShopAddress());
				sellerDTO.setShopName(seller.getShopName());
				sellerDTO.setEmailSeller(seller.getEmailSeller());
				sellerDTO.setPhoneNumberSeller(seller.getPhoneNumberSeller());
				orderDTO.setSeller(sellerMapper.toEntity(sellerDTO));
			}

			orderDTO.setOrderItems(
					order.getOrderItems().stream().map(orderItemsMapper::toDTO).collect(Collectors.toList()));
			return orderDTO;
		}).collect(Collectors.toList());
	}

	public OrderDTO getOrderById(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));

		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setOrderDate(order.getOrderDate());
		orderDTO.setPrice(order.getPrice());

		// Avoid setting seller if it's null
		if (order.getSeller() != null) {
			Seller seller = order.getSeller();
			SellerDTO sellerDTO = new SellerDTO();
			sellerDTO.setShopAddress(seller.getShopAddress());
			sellerDTO.setShopName(seller.getShopName());
			sellerDTO.setEmailSeller(seller.getEmailSeller());
			sellerDTO.setPhoneNumberSeller(seller.getPhoneNumberSeller());
			orderDTO.setSeller(sellerMapper.toEntity(sellerDTO));
		}

		orderDTO.setOrderItems(
				order.getOrderItems().stream().map(orderItemsMapper::toDTO).collect(Collectors.toList()));

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
