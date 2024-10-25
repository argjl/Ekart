package com.ekart.springekartapplication.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.ekart.springekartapplication.Entity.OrderItems;
import com.ekart.springekartapplication.Entity.Seller;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OrderDTO {
	private Long id;
	private LocalDateTime orderDate;
	private Long price;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Seller seller=null;
    @JsonProperty("orderItems")
	private List<OrderItemsDTO> orderItems;
}
