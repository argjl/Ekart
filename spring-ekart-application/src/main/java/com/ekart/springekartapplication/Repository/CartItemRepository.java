package com.ekart.springekartapplication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ekart.springekartapplication.Entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	@Modifying
	@Query("DELETE FROM CartItem ci WHERE ci.product.id=:productId")
	void deleteByProductId(@Param("productId")Long productId);
	
	Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
	

}
