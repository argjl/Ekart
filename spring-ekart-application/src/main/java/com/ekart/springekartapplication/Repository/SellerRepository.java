package com.ekart.springekartapplication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ekart.springekartapplication.Entity.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

	public Optional<Seller> findByUsername(String username);

}
