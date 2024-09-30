package com.ekart.springekartapplication.Entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name ="category")
@Data
@JsonIgnoreProperties("products") // Ignore the products field to avoid infinite recursion
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String categoryName;
	
	@JsonBackReference
	@OneToMany(mappedBy = "category")
	private List<Product> products;

}
