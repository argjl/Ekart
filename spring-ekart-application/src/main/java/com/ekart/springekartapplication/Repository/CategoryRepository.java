package com.ekart.springekartapplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import com.ekart.springekartapplication.DTO.CategoryDTO;
import com.ekart.springekartapplication.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	ResponseEntity<?> save(CategoryDTO newCategory);

	boolean existsByCategoryName(String categoryName);

}
