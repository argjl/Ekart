package com.ekart.springekartapplication.Mapper;

import com.ekart.springekartapplication.DTO.UserDTO;
import com.ekart.springekartapplication.Entity.User;

public class UserMapper {
	public static UserDTO toDTO(User user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		dto.setRole(user.getRole());
		return dto;
	}

	public static User toEntity(UserDTO dto) {
		User user = new User();
		user.setId(dto.getId());
		user.setUsername(dto.getUsername());
		user.setRole(dto.getRole());
		// Handle password if required (typically, it wouldn't be part of the DTO)
		return user;
	}
}
