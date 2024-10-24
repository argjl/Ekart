package com.ekart.springekartapplication.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ekart.springekartapplication.DTO.CustomerDTO;
import com.ekart.springekartapplication.DTO.CustomerResponseDTO;
import com.ekart.springekartapplication.DTO.SellerDTO;
import com.ekart.springekartapplication.DTO.SellerResponseDTO;
import com.ekart.springekartapplication.Entity.Customer;
import com.ekart.springekartapplication.Entity.Seller;
import com.ekart.springekartapplication.Entity.User;
import com.ekart.springekartapplication.Repository.CustomerRepository;
import com.ekart.springekartapplication.Repository.SellerRepository;
import com.ekart.springekartapplication.Repository.UserRepository;

@Service
public class UserService {

	Logger logger = LogManager.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public CustomerResponseDTO createCustomer(CustomerDTO customerDTO) {
		logger.info("UserService : createCustomer Request from Controller Processing");
		logger.info("UserService : createCustomer Request from Controller {}", customerDTO);
		if (userRepository.existsByUsername(customerDTO.getUsername())) {
			throw new DataIntegrityViolationException("Username is Already Taken");
		}
		Customer customer = new Customer();
		customer.setUsername(customerDTO.getUsername());
		customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
		customer.setRole("CUSTOMER");
		customer.setEmailCustomer(customerDTO.getEmailCustomer());
		customer.setPhoneNumberCustomer(customerDTO.getPhoneNumberCustomer());
		customer.setAddress(customerDTO.getAddress());

		Customer savedCustomer = customerRepository.save(customer);
		CustomerResponseDTO responseCustomerDTO = new CustomerResponseDTO();
		responseCustomerDTO.setUsername(savedCustomer.getUsername());
		responseCustomerDTO.setEmailCustomer(savedCustomer.getEmailCustomer());
		responseCustomerDTO.setPhoneNumberCustomer(savedCustomer.getPhoneNumberCustomer());
		responseCustomerDTO.setAddress(savedCustomer.getAddress());

		return responseCustomerDTO;
	}

	public SellerResponseDTO createSeller(SellerDTO sellerDTO) {
		logger.info("UserService : createSeller Request from Controller Processing");
		logger.info("UserService : createSeller Request from Controller {}", sellerDTO);
		if (userRepository.existsByUsername(sellerDTO.getUsername())) {
			throw new DataIntegrityViolationException("Username is Already Taken");
		}
		Seller seller = new Seller();
		seller.setUsername(sellerDTO.getUsername());
		seller.setPassword(passwordEncoder.encode(sellerDTO.getPassword()));
		seller.setRole("SELLER");
		seller.setEmailSeller(sellerDTO.getEmailSeller());
		seller.setPhoneNumberSeller(sellerDTO.getPhoneNumberSeller());
		seller.setShopAddress(sellerDTO.getShopAddress());
		seller.setShopName(sellerDTO.getShopName());

		Seller savedSeller = sellerRepository.save(seller);
		SellerResponseDTO responseSellerDTO = new SellerResponseDTO();
		responseSellerDTO.setUsername(savedSeller.getUsername());
		responseSellerDTO.setEmailSeller(savedSeller.getEmailSeller());
		responseSellerDTO.setPhoneNumberSeller(savedSeller.getPhoneNumberSeller());
		responseSellerDTO.setShopAddress(savedSeller.getShopAddress());
		responseSellerDTO.setShopName(savedSeller.getShopName());

		return responseSellerDTO;

	}

}
