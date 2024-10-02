package com.ekart.springekartapplication.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.ekart.springekartapplication.Entity.OrderItems;
import com.ekart.springekartapplication.Entity.Seller;

import lombok.Data;

@Data
public class OrderDTO {
	private Long id;
	private LocalDateTime orderDate;
	private Long price;
	private Seller seller;
	private List<OrderItems> orderItems;
}
