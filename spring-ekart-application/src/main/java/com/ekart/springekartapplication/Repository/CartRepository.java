package com.ekart.springekartapplication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekart.springekartapplication.Entity.Cart;
import com.ekart.springekartapplication.Entity.Product;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Optional<Cart> findById(Long customerId);

	Optional<Cart> findByCustomerId(Long id);
}
