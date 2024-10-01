package com.ekart.springekartapplication.Mapper;

import org.springframework.stereotype.Component;

import com.ekart.springekartapplication.DTO.CartItemDTO;
import com.ekart.springekartapplication.Entity.CartItem;

@Component
public class CartItemMapper {

    public CartItemDTO toDTO(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setProductId(cartItem.getProduct().getId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        return cartItemDTO;
    }

    public CartItem toEntity(CartItemDTO cartItemDTO) {
        if (cartItemDTO == null) {
            return null;
        }
        CartItem cartItem = new CartItem();
        // Assume that Product entity is fetched from the repository based on productId
        // This may require passing a ProductRepository as a dependency
        // For simplicity, this example assumes you set product elsewhere
        cartItem.setQuantity(cartItemDTO.getQuantity());
        return cartItem;
    }
}
