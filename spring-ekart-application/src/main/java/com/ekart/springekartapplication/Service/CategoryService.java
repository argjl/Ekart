package com.ekart.springekartapplication.Service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.ekart.springekartapplication.Controller.CategoryController;
import com.ekart.springekartapplication.DTO.CategoryDTO;
import com.ekart.springekartapplication.Entity.Category;
import com.ekart.springekartapplication.Repository.CategoryRepository;

@Service
public class CategoryService {

	Logger logger = LogManager.getLogger(CategoryController.class);

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

	public CategoryDTO addCategory(CategoryDTO categoryDTO) {
		if (categoryDTO.getId() != null) {
			throw new RuntimeException("Category ID should not be provided.");
		}

		// Check if a category with the same name already exists
		if (categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())) {
			throw new RuntimeException("Category with the same name already exists.");
		}

		Category newCategory = new Category();
		newCategory.setCategoryName(categoryDTO.getCategoryName());
		logger.info("CategoryService:addCategory");

		try {
			// Save the category entity
			Category savedCategory = categoryRepository.save(newCategory);

			// Map saved entity back to DTO
			CategoryDTO savedCategoryDTO = new CategoryDTO();
			savedCategoryDTO.setId(savedCategory.getId());
			savedCategoryDTO.setCategoryName(savedCategory.getCategoryName());

			return savedCategoryDTO; // Return DTO instead of entity
		} catch (DataIntegrityViolationException e) {
			logger.error("Error saving category: ", e);
			throw new RuntimeException("An error occurred while saving the category.");
		}
	}

}
