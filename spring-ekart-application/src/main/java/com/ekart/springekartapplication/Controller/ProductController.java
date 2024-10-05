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

import com.ekart.springekartapplication.DTO.ProductDTO;
import com.ekart.springekartapplication.DTO.ResponseDTO;
import com.ekart.springekartapplication.Entity.Product;
import com.ekart.springekartapplication.Service.ProductService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private Gson gson;

	@PostMapping("/addProduct")
	public ResponseEntity<String> addProduct(@RequestBody ProductDTO product) {
		Product saveProduct = productService.addProduct(product);
		String jsonResponse = gson.toJson(new ResponseDTO<>("Product has been Added Succesfully", saveProduct));
		return ResponseEntity.ok(jsonResponse);
	}

	@PostMapping("/updateProduct")
	public ResponseEntity<String> updateProduct(@RequestBody ProductDTO productDTO) {
		Product saveProduct = productService.updateProduct(productDTO);
		String jsonResponse = gson.toJson(new ResponseDTO<>("Product has been updated Succesfully", saveProduct));
		return ResponseEntity.ok(jsonResponse);
	}

	@GetMapping("/view")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return ResponseEntity.ok(products);

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

//	@DeleteMapping("/delete/{productId}")
//	public ResponseEntity<List<Product>> deleteProducts(@PathVariable Long productId) {
//		List<Product> updatedProducts=productService.deleteProduct(productId);
//		return ResponseEntity.ok(updatedProducts);
//	}

}
