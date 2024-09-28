package com.ekart.springekartapplication.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ekart.springekartapplication.Entity.Category;
import com.ekart.springekartapplication.Entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByNameContaining(String name);

	List<Product> findByCategory(Category category);

	List<Product> findByPriceBetween(Long minimumPrice, Long maximumPrice);

	List<Product> findBySellerId(Long sellerId);

	Optional<Product> findByIdAndSellerId(Long productId, Long sellerId);

	@Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.price BETWEEN :minimumPrice AND :maximumPrice")
	List<Product> findByCategoryIdAndPriceBetween(@Param("categoryId") Long categoryId, @Param("minimumPrice") Long minimumPrice, @Param("maximumPrice") Long maximumPrice);
}
