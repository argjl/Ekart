package com.ekart.springekartapplication.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
//import java.util.UUID;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;

import com.ekart.springekartapplication.Entity.Customer; // Adjust the import based on your Customer entity
import com.ekart.springekartapplication.Entity.Seller;
import com.ekart.springekartapplication.Repository.CustomerRepository;
import com.ekart.springekartapplication.Repository.SellerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	Logger logger = LogManager.getLogger(CustomUserDetailsService.class);

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private CustomerRepository customerRepository; // Assuming you have a CustomerRepository

//	@Autowired
//	private SplunkLoggingService splunkLoggingService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//	    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//	    HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

		// Check for Seller
		Optional<Seller> sellerOptional = sellerRepository.findByUsername(username);
		if (sellerOptional.isPresent()) {
			Seller seller = sellerOptional.get();
			logger.info("Seller found: {}", username);
//	        splunkLoggingService.logRequestAndResponse(request, response, "Seller found: " + username);
			return new org.springframework.security.core.userdetails.User(seller.getUsername(), seller.getPassword(),
					getAuthorities(seller));
		}

		// Check for Customer
		Optional<Customer> customerOptional = customerRepository.findByUsername(username);
		if (customerOptional.isPresent()) {
			Customer customer = customerOptional.get();
			logger.info("Customer found: {}", username);
//	        splunkLoggingService.logRequestAndResponse(request, response, "Customer found: " + username);
			return new org.springframework.security.core.userdetails.User(customer.getUsername(),
					customer.getPassword(), getAuthorities(customer));
		}

		logger.warn("User not found: {}", username);
		throw new UsernameNotFoundException("User not found with username: " + username);
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Seller seller) {
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_SELLER"));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Customer customer) {
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
	}
}