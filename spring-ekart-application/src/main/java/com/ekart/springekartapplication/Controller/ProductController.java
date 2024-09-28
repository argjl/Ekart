package com.ekart.springekartapplication.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.springekartapplication.Entity.Product;
import com.ekart.springekartapplication.Service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/addProduct")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		Product saveProduct = productService.addProduct(product);
		return ResponseEntity.ok(saveProduct);
	}

	@GetMapping("/search")
	public List<Product> searchProducts(@RequestParam String name) {
		return productService.searchProducts(name);
	}

	@GetMapping("/filter")
	public List<Product> filterProducts(@RequestParam Long categoryId, @RequestParam Long minimumPrice,
			@RequestParam Long maximumPrice) {
		return productService.filterProducts(categoryId, minimumPrice, maximumPrice);
	}

}
