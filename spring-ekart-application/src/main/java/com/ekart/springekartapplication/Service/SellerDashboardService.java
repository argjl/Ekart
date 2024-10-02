package com.ekart.springekartapplication.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ekart.springekartapplication.DTO.ProductDTO;
import com.ekart.springekartapplication.Entity.Category;
import com.ekart.springekartapplication.Entity.Order;
import com.ekart.springekartapplication.Entity.Product;
import com.ekart.springekartapplication.Entity.Seller;
import com.ekart.springekartapplication.Exception.CategoryNotFoundException;
import com.ekart.springekartapplication.Exception.InvalidProductException;
import com.ekart.springekartapplication.Exception.ProductNotFoundException;
import com.ekart.springekartapplication.Exception.SellerNotFoundException;
import com.ekart.springekartapplication.Mapper.ProductMapper;
import com.ekart.springekartapplication.Repository.CartItemRepository;
import com.ekart.springekartapplication.Repository.CategoryRepository;
import com.ekart.springekartapplication.Repository.OrderRespository;
import com.ekart.springekartapplication.Repository.ProductRepository;
import com.ekart.springekartapplication.Repository.SellerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
public class SellerDashboardService {

	Logger logger = LoggerFactory.getLogger(SellerDashboardService.class);

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRespository orderRepository;

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public List<ProductDTO> getSellerProducts(Long sellerId) {
		List<Product> products = productRepository.findBySellerId(sellerId);
		return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
	}

	// Add or Update product
	public Product addOrUpdateProduct(Product product, Seller seller) {
		// Validate product
		validateProduct(product);
		logger.info("SellerDashboardService : addOrUpdateProduct Request from Controller {}", product);
		validateSeller(seller);
		if (product.getId() != null) {
			Optional<Product> existingProduct = productRepository.findById(product.getId());
			if (existingProduct.isPresent()) {
				validateProductOwnership(existingProduct.get(), seller);
			} else {
				throw new ProductNotFoundException(product.getId());
			}
		}
		product.setSeller(seller);
		logger.info("SellerDashboardService : addOrUpdateProduct Response to Controller {}", product);
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

	public Seller findSellerByUsername(String username) {
		Optional<Seller> sellerOptional = sellerRepository.findByUsername(username);
		// Ensure that all fields are being fetched properly
		Seller seller = sellerOptional
				.orElseThrow(() -> new SellerNotFoundException("Seller not found with username: " + username));

		// Verify that shop details are being fetched
		if (seller.getShopName() == null || seller.getShopAddress() == null || seller.getEmailSeller() == null
				|| seller.getPhoneNumberSeller() == null) {
			StringBuilder missingFields = new StringBuilder("Missing fields: ");

			if (seller.getShopName() == null) {
				missingFields.append("shopName, ");
			}
			if (seller.getShopAddress() == null) {
				missingFields.append("shopAddress, ");
			}
			if (seller.getEmailSeller() == null) {
				missingFields.append("emailSeller, ");
			}
			if (seller.getPhoneNumberSeller() == null) {
				missingFields.append("phoneNumberSeller, ");
			}
			throw new IllegalStateException("Incomplete shop details: one or more fields are missing.");
		}

		return seller;
	}

	public Category findCategory(Long categoryId) {
		Optional<Category> category = categoryRepository.findById(categoryId);
		logger.info("SellerDashboardService : findCategory Category {}", category);
		if (!category.isPresent()) {
			throw new CategoryNotFoundException(categoryId);
		}

		return category.get();
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

	private void validateSeller(Seller seller) {
		if (seller == null || seller.getId() == null) {
			throw new SellerNotFoundException("Seller not found");
		}

		Optional<Seller> existingSeller = sellerRepository.findById(seller.getId());
		if (!existingSeller.isPresent()) {
			throw new SellerNotFoundException("Seller with ID " + seller.getId() + " not found.");
		}
	}

	private void validateProductOwnership(Product existingProduct, Seller seller) {
		if (!existingProduct.getSeller().getId().equals(seller.getId())) {
			throw new RuntimeException("Seller is not authorized to update this product");
		}
	}
}
