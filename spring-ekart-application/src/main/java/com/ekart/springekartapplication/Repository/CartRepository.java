package com.ekart.springekartapplication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ekart.springekartapplication.Entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	Optional<Cart> findById(Long customerId);

	Optional<Cart> findByCustomerId(Long id);

}
