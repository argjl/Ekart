package com.ekart.springekartapplication.Mapper;

import com.ekart.springekartapplication.DTO.CategoryDTO;
import com.ekart.springekartapplication.DTO.ProductDTO;
import com.ekart.springekartapplication.DTO.SellerDTO;
import com.ekart.springekartapplication.Entity.Category;
import com.ekart.springekartapplication.Entity.Product;
import com.ekart.springekartapplication.Entity.Seller;

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
		// Map Seller
//		if(product.getSeller()!=null) {
//			Seller seller=product.getSeller();
//			SellerDTO sellerDTO=new SellerDTO();
//			sellerDTO.setId(seller.getId());
//			sellerDTO.setShopAddress(seller.getShopAddress());
//			sellerDTO.setEmailSeller(seller.getEmailSeller());
//			sellerDTO.setShopName(seller.getShopName());
//			sellerDTO.setPhoneNumberSeller(seller.getPhoneNumberSeller());
//			dto.setSeller(sellerDTO);
//		}
//        // Map Category
//        if (product.getCategory() != null) {
//            Category category = product.getCategory();
//            CategoryDTO categoryDTO = new CategoryDTO();
//            categoryDTO.setId(category.getId());
//            categoryDTO.setCategoryName(category.getCategoryName());
//            dto.setCategory(categoryDTO);
//        }

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
