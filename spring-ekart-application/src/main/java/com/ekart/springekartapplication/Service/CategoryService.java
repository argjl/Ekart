package com.ekart.springekartapplication.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ekart.springekartapplication.Controller.CategoryController;
import com.ekart.springekartapplication.DTO.CategoryDTO;
import com.ekart.springekartapplication.Entity.Category;
import com.ekart.springekartapplication.Repository.CategoryRepository;

@Service
public class CategoryService {

	Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> getAllCategories() {
		logger.info("CategoryService:getAllCategories");
		return categoryRepository.findAll();
	}

//	public ResponseEntity<?> addCategory(CategoryDTO categoryDTO) {
//		if (categoryDTO.getId() != null) {
//			throw new RuntimeException("Category ID should not be provided.");
//		}
//	       // Check if a category with the same name already exists
//        if (categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())) {
//            throw new RuntimeException("Category name must be unique.");
//        }
//
//		// Validate categoryName
//		if (categoryDTO.getCategoryName() == null || categoryDTO.getCategoryName().isEmpty()) {
//			throw new RuntimeException("Category name must not be null or empty.");
//		}
//		Category newCategory = new Category();
//		newCategory.setCategoryName(categoryDTO.getCategoryName());
//		logger.info("CategoryService:addCategory");
//		return categoryRepository.save(newCategory);
//		   try {
//		        // Save the category entity
//		        Category savedCategory = categoryRepository.save(newCategory);
//		        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
//		    } catch (DataIntegrityViolationException e) {
//		        throw new RuntimeException("Category name must be unique.");
//		    }
//
//	}
	
	
	public ResponseEntity<?> addCategory(CategoryDTO categoryDTO) {
	    if (categoryDTO.getId() != null) {
	        throw new RuntimeException("Category ID should not be provided.");
	    }
	    
	    // Check if a category with the same name already exists
	    if (categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())) {
	        return ResponseEntity.status(HttpStatus.CONFLICT)
	                .body("Category with the same name already exists.");
	    }

	    Category newCategory = new Category();
	    newCategory.setCategoryName(categoryDTO.getCategoryName());
	    logger.info("CategoryService:addCategory");
	    
	    try {
	        // Save the category entity
	        Category savedCategory = categoryRepository.save(newCategory);
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
	    } catch (DataIntegrityViolationException e) {
	        logger.error("Error saving category: ", e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while saving the category.");
	    }
	}


}
