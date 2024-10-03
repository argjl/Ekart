package com.ekart.springekartapplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.springekartapplication.DTO.CustomerDTO;
import com.ekart.springekartapplication.DTO.CustomerResponseDTO;
import com.ekart.springekartapplication.DTO.ResponseDTO;
import com.ekart.springekartapplication.DTO.SellerDTO;
import com.ekart.springekartapplication.DTO.SellerResponseDTO;
import com.ekart.springekartapplication.Service.UserService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private Gson gson;

	@PostMapping("/customers")
	public ResponseEntity<String> createCustomer(@RequestBody CustomerDTO customerDTO) {
		CustomerResponseDTO createdCustomer = userService.createCustomer(customerDTO);
		String jsonResponse = gson.toJson(new ResponseDTO<>("Customer Added Succesfully", createdCustomer));
		return ResponseEntity.ok(jsonResponse);
	}

	@PostMapping("/sellers")
	public ResponseEntity<String> createSeller(@RequestBody SellerDTO sellerDTO) {
		SellerResponseDTO createdSeller = userService.createSeller(sellerDTO);
		String jsonResponse = gson.toJson(new ResponseDTO<>("Seller Added Succesfully", createdSeller));
		return ResponseEntity.ok(jsonResponse);
	}
}
