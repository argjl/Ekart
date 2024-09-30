package com.ekart.springekartapplication.Mapper;

import com.ekart.springekartapplication.DTO.ProductDTO;
import com.ekart.springekartapplication.Entity.Product;

public class ProductMapper {
	public static ProductDTO toDTO(Product product) {
		ProductDTO dto = new ProductDTO();
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		dto.setQuantity(product.getQuantity());
		dto.setSeller(SellerMapper.toDTO(product.getSeller()));
		dto.setCategory(CategoryMapper.toDTO(product.getCategory()));
		return dto;
	}

	public static Product toEntity(ProductDTO dto) {
		Product product = new Product();
		product.setId(dto.getId());
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setQuantity(dto.getQuantity());
		product.setSeller(SellerMapper.toEntity(dto.getSeller()));
		product.setCategory(CategoryMapper.toEntity(dto.getCategory()));
		return product;
	}
}
