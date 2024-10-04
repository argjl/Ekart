package com.ekart.springekartapplication.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ekart.springekartapplication.DTO.ProductDTO;
import com.ekart.springekartapplication.Entity.Category;
import com.ekart.springekartapplication.Entity.Product;
import com.ekart.springekartapplication.Entity.Seller;
import com.ekart.springekartapplication.Exception.InvalidProductException;
import com.ekart.springekartapplication.Exception.ProductNotFoundException;
import com.ekart.springekartapplication.Exception.SellerNotFoundException;
import com.ekart.springekartapplication.Mapper.CategoryMapper;
import com.ekart.springekartapplication.Mapper.ProductMapper;
import com.ekart.springekartapplication.Mapper.SellerMapper;
import com.ekart.springekartapplication.Repository.CategoryRepository;
import com.ekart.springekartapplication.Repository.ProductRepository;
import com.ekart.springekartapplication.Repository.SellerRepository;

@Service
public class ProductService {

	Logger logger = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public Product addProduct(ProductDTO productDTO) {
		// Validate the Product Details
		validateProduct(productDTO);
		logger.info("entered validate");

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		// Check if the seller exists

		// Save and return the Product

		Optional<Seller> existingSeller = sellerRepository.findByUsername(username);// Assuming Seller is Logged in
		logger.info(username);
//		logger.info(existingSeller.get().getId().toString());
		if (!existingSeller.isPresent()) {
			throw new RuntimeException("Seller not found"+ username);
		}
		Seller seller = existingSeller.get();
		productDTO.setSeller(SellerMapper.toDTO(seller));
		logger.info("Product is added !");
//		logger.info(productDTO.toString());
		return productRepository.save(ProductMapper.toEntity(productDTO));
	}

	// Update an existing product
	public Product updateProduct(ProductDTO productDTO) {
		// Validate the updated product details
		validateProduct(productDTO);

		// Check if the product exists
		Product existingProduct = productRepository.findById(productDTO.getId())
				.orElseThrow(() -> new ProductNotFoundException(productDTO.getId()));

		// Update product fields
		existingProduct.setName(productDTO.getName());
		existingProduct.setDescription(productDTO.getDescription());
		existingProduct.setPrice(productDTO.getPrice());
		existingProduct.setQuantity(productDTO.getQuantity());
		existingProduct.setCategory(CategoryMapper.toEntity(productDTO.getCategory()));

		// Save and return the updated product
		return productRepository.save(existingProduct);
	}

	// Delete a product
	public void deleteProduct(Long productId) {
		// Check if the product exists before deleting
		if (!productRepository.existsById(productId)) {
			throw new ProductNotFoundException(productId);
		}
		productRepository.deleteById(productId);
	}

	// Search products by name
	public List<Product> searchProducts(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new InvalidProductException("Search Item Cannot Be Null or empty");
		}
		List<Product> products = productRepository.findByNameContaining(name);
		if (products.isEmpty()) {
			throw new InvalidProductException("No Products found Matching the Search term: " + name);
		}
		return products;
	}

	// Filter products by category and price range
	public List<Product> filterProducts(Long categoryId, Long minPrice, Long maxPrice) {

		if (categoryId == null) {
			throw new InvalidProductException("Category ID cannot be Null");
		}
		if (minPrice == null || maxPrice == null || minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
			throw new InvalidProductException("Invalid Price Range");
		}

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Category Not Found"));
		List<Product> products = productRepository.findByCategoryIdAndPriceBetween(categoryId, minPrice, maxPrice);
		if (products.isEmpty()) {
			throw new InvalidProductException("No Products found for the Specified filter");
		}
		return products;
	}

	// Get All Products
	public List<Product> getAllProducts() {
		return productRepository.findAll().stream().distinct().collect(Collectors.toList());
	}

	// Get Products by Seller ID
	public List<Product> getProductsBySellerId(Long sellerId) {
		// Check if the seller exists
		if (!sellerRepository.existsById(sellerId)) {
			throw new SellerNotFoundException(sellerId);
		}

		// Fetch and return the list of products for the seller
		List<Product> products = productRepository.findBySellerId(sellerId);
		if (products.isEmpty()) {
			throw new ProductNotFoundException(sellerId);
		}

		return products;
	}

	private void validateProduct(ProductDTO product) {
		logger.info("entering Validate");
		if (product.getName() == null || product.getName().trim().isEmpty()) {
			throw new InvalidProductException("Product Name Cannot be NULL or Empty");
		}
		if (product.getPrice() == null || product.getPrice() <= 0) {
			throw new InvalidProductException("Product price must be a positive Value");
		}
		if (product.getQuantity() < 0) {
			throw new InvalidProductException("Product Quantity cannnot be Negative");
		}

	}
}
