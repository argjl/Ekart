package com.ekart.springekartapplication.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ekart.springekartapplication.Entity.Customer; // Adjust the import based on your Customer entity
import com.ekart.springekartapplication.Entity.Seller;
import com.ekart.springekartapplication.Repository.CustomerRepository;
import com.ekart.springekartapplication.Repository.SellerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private SellerRepository sellerRepository;
    
    @Autowired
    private CustomerRepository customerRepository; // Assuming you have a CustomerRepository

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check for Seller
        Optional<Seller> sellerOptional = sellerRepository.findByUsername(username);
        if (sellerOptional.isPresent()) {
            Seller seller = sellerOptional.get();
            logger.info("Seller found: {} with password: {}", username, seller.getPassword());
            return new org.springframework.security.core.userdetails.User(seller.getUsername(), seller.getPassword(), getAuthorities(seller));
        }
        
        // Check for Customer
        Optional<Customer> customerOptional = customerRepository.findByUsername(username); // Adjust method as needed
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            logger.info("Customer found: {} with password: {}", username, customer.getPassword());
            return new org.springframework.security.core.userdetails.User(customer.getUsername(), customer.getPassword(), getAuthorities(customer));
        }

        logger.warn("User not found: {}", username);
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Seller seller) {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_SELLER")); // Seller role
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(Customer customer) {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_CUSTOMER")); // Customer role
    }
}
