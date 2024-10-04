package com.ekart.springekartapplication.Mapper;

import org.springframework.stereotype.Component;

import com.ekart.springekartapplication.DTO.CategoryDTO;
import com.ekart.springekartapplication.Entity.Category;

@Component
public class CategoryMapper {
	public static CategoryDTO toDTO(Category category) {
		CategoryDTO dto = new CategoryDTO();
		dto.setId(category.getId());
		dto.setCategoryName(category.getCategoryName());
		return dto;
	}

	public static Category toEntity(CategoryDTO dto) {
		Category category = new Category();
		category.setId(dto.getId());
		category.setCategoryName(dto.getCategoryName());
		return category;
	}
}
