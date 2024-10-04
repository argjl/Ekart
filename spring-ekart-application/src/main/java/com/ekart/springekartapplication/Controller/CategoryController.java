package com.ekart.springekartapplication.Controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.springekartapplication.DTO.CategoryDTO;
import com.ekart.springekartapplication.DTO.ResponseDTO;
import com.ekart.springekartapplication.Entity.Category;
import com.ekart.springekartapplication.Service.CategoryService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/category")
public class CategoryController {

	Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private Gson gson;

	@GetMapping("/viewCategory")
	public List<Category> getCategories() {

		logger.info("CategoryController:getCategories");
		return categoryService.getAllCategories();
	}

	@PostMapping("/addCategory")
	public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		logger.info("CategoryController:Add Category");
		CategoryDTO createdCategory = categoryService.addCategory(categoryDTO);
		String jsonResponse = gson.toJson(new ResponseDTO<>("Category has been added Successfully", createdCategory));
		return ResponseEntity.ok(jsonResponse);
	}

}
