package com.ekart.springekartapplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekart.springekartapplication.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
