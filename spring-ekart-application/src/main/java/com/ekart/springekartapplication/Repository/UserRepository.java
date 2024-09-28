package com.ekart.springekartapplication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekart.springekartapplication.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
//	Optional<User> findByUsername(String username);
	User findByUsername(String username); // Customize based on your user entity

}
