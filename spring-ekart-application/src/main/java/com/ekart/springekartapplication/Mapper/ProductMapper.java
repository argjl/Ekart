package com.ekart.springekartapplication.Mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ekart.springekartapplication.DTO.ProductDTO;
import com.ekart.springekartapplication.Entity.Product;

public class ProductMapper {
	static Logger logger = LoggerFactory.getLogger(ProductMapper.class);

	public static ProductDTO toDTO(Product product) {
		ProductDTO dto = new ProductDTO();
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		dto.setQuantity(product.getQuantity());
		// Add debug logging
		if (product.getSeller() != null) {
			logger.info("Seller found: {}", product.getSeller().getShopName());
		} else {
			logger.warn("Seller is null for product: {}", product.getName());
		}

		if (product.getCategory() != null) {
			logger.info("Category found: {}", product.getCategory().getCategoryName());
		} else {
			logger.warn("Category is null for product: {}", product.getName());
		}
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
