package com.ekart.springekartapplication.Mapper;

import com.ekart.springekartapplication.DTO.SellerDTO;
import com.ekart.springekartapplication.Entity.Seller;

public class SellerMapper extends UserMapper {
	public static SellerDTO toDTO(Seller seller) {
		SellerDTO dto = new SellerDTO();
		dto.setId(seller.getId());
		dto.setUsername(seller.getUsername());
		dto.setRole(seller.getRole());
		dto.setShopAddress(seller.getShopAddress());
		dto.setShopName(seller.getShopName());
		dto.setEmailSeller(seller.getEmailSeller());
		dto.setPhoneNumberSeller(seller.getPhoneNumberSeller());
		return dto;
	}

	public static Seller toEntity(SellerDTO dto) {
		Seller seller = new Seller();
		seller.setId(dto.getId());
		seller.setUsername(dto.getUsername());
		seller.setRole(dto.getRole());
		seller.setShopAddress(dto.getShopAddress());
		seller.setShopName(dto.getShopName());
		seller.setEmailSeller(dto.getEmailSeller());
		seller.setPhoneNumberSeller(dto.getPhoneNumberSeller());
		return seller;
	}
}
