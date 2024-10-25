package com.ekart.springekartapplication.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ekart.springekartapplication.Entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findOrderBySellerId(Long sellerId);

	List<Order> findByCustomerId(Long id);

}
