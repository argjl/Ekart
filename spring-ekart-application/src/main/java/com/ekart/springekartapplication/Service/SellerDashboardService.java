package com.ekart.springekartapplication.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ekart.springekartapplication.Entity.Order;
import com.ekart.springekartapplication.Entity.Product;
import com.ekart.springekartapplication.Entity.Seller;
import com.ekart.springekartapplication.Exception.InvalidProductException;
import com.ekart.springekartapplication.Exception.ProductNotFoundException;
import com.ekart.springekartapplication.Exception.SellerNotFoundException;
import com.ekart.springekartapplication.Repository.CartItemRepository;
import com.ekart.springekartapplication.Repository.OrderRespository;
import com.ekart.springekartapplication.Repository.ProductRepository;
import com.ekart.springekartapplication.Repository.SellerRepository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class SellerDashboardService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRespository orderRepository;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;

	public List<Product> getSellerProducts(Long sellerId) {
		return productRepository.findBySellerId(sellerId);
	}

	// Add or Update product
	public Product addOrUpdateProduct(Product product) {
		// Validate product
		validateProduct(product);
		return productRepository.save(product);
	}

	@Transactional
	public void deleteProductBySeller(Long productId, Long sellerId) {
		// Find the product by the it ID
		Optional<Product> existingProduct = productRepository.findById(productId);

		if (!existingProduct.isPresent()) {
			throw new ProductNotFoundException(productId);
		}

		// Check if the Product belongs to the Seller
		Product product = existingProduct.get();
		if (!product.getSeller().getId().equals(sellerId)) {
			throw new ProductNotFoundException(productId, sellerId);
		}
		cartItemRepository.deleteByProductId(productId);
		productRepository.deleteById(productId);
	}

	public List<Order> getSellerOrders(Long sellerId) {
		return orderRepository.findOrderBySellerId(sellerId);
	}

	private void validateProduct(Product product) {
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

	 public Seller findSellerByUsername(String username) {
	        Optional<Seller> sellerOptional = sellerRepository.findByUsername(username);
	        
	        // If not found, throw an exception
	        if (!sellerOptional.isPresent()) {
	            throw new SellerNotFoundException("Seller not found with username: " + username);
	        }

	        // Return the found seller
	        return sellerOptional.get();
	    }
}
