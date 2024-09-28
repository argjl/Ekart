package com.ekart.springekartapplication.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequest {

	private String username;
	private String password;

}
