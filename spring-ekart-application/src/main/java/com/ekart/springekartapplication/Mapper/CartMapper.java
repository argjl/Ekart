package com.ekart.springekartapplication.Mapper;

import java.util.stream.Collectors;

import com.ekart.springekartapplication.DTO.CartDTO;
import com.ekart.springekartapplication.DTO.CartItemDTO;
import com.ekart.springekartapplication.Entity.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartMapper {

	@Autowired
	private CartItemMapper cartItemMapper;

	public CartDTO toDTO(Cart cart) {
		if (cart == null) {
			return null;
		}
		CartDTO cartDTO = new CartDTO();
		cartDTO.setId(cart.getId());
		cartDTO.setCustomerId(cart.getCustomer().getId());
		List<CartItemDTO> itemDTOs = cart.getItems().stream().map(cartItemMapper::toDTO).collect(Collectors.toList());
		cartDTO.setItems(itemDTOs);
		return cartDTO;
	}

	public Cart toEntity(CartDTO cartDTO) {
		if (cartDTO == null) {
			return null;
		}
		Cart cart = new Cart();
		// Set customer and other fields if necessary
		cart.setId(cartDTO.getId());
		return cart;
	}
}
