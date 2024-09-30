package com.ekart.springekartapplication.DTO;

import lombok.Data;

@Data
public class UserDTO {
	private Long id;
	private String username;
	private String role;
	// No password for security reasons in the DTO
}
