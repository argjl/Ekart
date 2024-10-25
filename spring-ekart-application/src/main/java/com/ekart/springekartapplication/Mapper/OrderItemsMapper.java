package com.ekart.springekartapplication.Mapper;

import org.springframework.stereotype.Component;

import com.ekart.springekartapplication.DTO.OrderItemsDTO;
import com.ekart.springekartapplication.Entity.OrderItems;

@Component
public class OrderItemsMapper {

	public OrderItemsDTO toDTO(OrderItems orderItems) {
		OrderItemsDTO dto = new OrderItemsDTO();
		dto.setId(orderItems.getId());
		dto.setProduct(ProductMapper.toDTO(orderItems.getProduct()));
		dto.setQuantity(orderItems.getQuantity());
		return dto;
	}

	public static OrderItems toEntity(OrderItemsDTO dto) {
		OrderItems orderItems = new OrderItems();
		orderItems.setId(dto.getId());
		orderItems.setProduct(ProductMapper.toEntity(dto.getProduct()));
		orderItems.setQuantity(dto.getQuantity());
		return orderItems;
	}
}
