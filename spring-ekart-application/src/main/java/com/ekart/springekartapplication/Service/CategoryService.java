package com.ekart.springekartapplication.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ekart.springekartapplication.Entity.Category;
import com.ekart.springekartapplication.Repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	public Category addCategory(Category category) {
		return categoryRepository.save(category);
	}

}
