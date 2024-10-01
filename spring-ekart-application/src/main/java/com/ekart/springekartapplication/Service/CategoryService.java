package com.ekart.springekartapplication.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

	public ResponseEntity<?> addCategory(CategoryDTO categoryDTO) {
		if (categoryDTO.getId() != null) {
			throw new RuntimeException("Category ID should not be provided.");
		}

		// Validate categoryName
		if (categoryDTO.getCategoryName() == null || categoryDTO.getCategoryName().isEmpty()) {
			throw new RuntimeException("Category name must not be null or empty.");
		}
		CategoryDTO newCategory = new CategoryDTO();
		newCategory.setCategoryName(categoryDTO.getCategoryName());
		logger.info("CategoryService:addCategory");
		return categoryRepository.save(newCategory);

	}

}
