package com.ekart.springekartapplication.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.springekartapplication.Entity.Product;
import com.ekart.springekartapplication.Entity.Seller;
import com.ekart.springekartapplication.Exception.CategoryNotFoundException;
import com.ekart.springekartapplication.Exception.SellerNotFoundException;
import com.ekart.springekartapplication.Mapper.CategoryMapper;
import com.ekart.springekartapplication.Mapper.ProductMapper;
import com.ekart.springekartapplication.Mapper.SellerMapper;
import com.ekart.springekartapplication.Repository.SellerRepository;
import com.ekart.springekartapplication.DTO.ProductDTO;
import com.ekart.springekartapplication.Entity.Category;
import com.ekart.springekartapplication.Entity.Order;
import com.ekart.springekartapplication.Service.ProductService;
import com.ekart.springekartapplication.Service.SellerDashboardService;
import com.ekart.springekartapplication.Service.SplunkLoggingService;

@RestController
@RequestMapping("/seller/dashboard")
public class SellerDashboardController {

	Logger logger = LogManager.getLogger(SellerDashboardController.class);

	@Autowired
	private SellerDashboardService sellerDashboardService;

	@Autowired
	private ProductService productService;

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private SplunkLoggingService splunkLoggingService;

	String requestMethod;

	/**
	 * Get all products added by the seller
	 *
	 * @param authentication the seller's authentication object
	 * @return the list of products added by the seller
	 */
	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> getSellerProducts(HttpServletRequest request,
			HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();

		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			Seller seller = sellerDashboardService.findSellerByUsername(username);

			List<Product> products = productService.getProductsBySellerId(seller.getId());
			List<ProductDTO> productdtos = products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());

			// Log request and response details
			String responseBody = String.format("SellerDashboardController : Product Details for the User %s %s",
					username, productdtos.toString());
			response.setStatus(HttpStatus.OK.value());
			splunkLoggingService.logRequestAndResponse(request, response, responseBody);

			return ResponseEntity.ok(productdtos);
		}
		logger.error("Unauthorized access attempt");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		splunkLoggingService.logRequestAndResponse(request, response, "Unauthorized access attempt");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	/**
	 * Add or update a product for the seller
	 *
	 * @param product        the product to add or update
	 * @param authentication the seller's authentication object
	 * @return the added or updated product
	 */
	@PostMapping("/addOrUpdateProduct")
	public ResponseEntity<String> addOrUpdateProduct(@RequestBody ProductDTO productDTO, Authentication authentication,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("SellerDashboardController : Entering addOrUpdateProduct");
		String username = authentication.getName();
		logger.info("SellerDashboardController : addOrUpdateProduct Request Sent {}", productDTO);
		Seller seller = sellerDashboardService.findSellerByUsername(username); // Assuming seller is logged in
		if (seller == null) {
			throw new SellerNotFoundException(username);
		}
		Category category = sellerDashboardService.findCategory(productDTO.getCategory().getId());
		if (category == null) {
			throw new CategoryNotFoundException(productDTO.getCategory().getId());
		}

		productDTO.setSeller(SellerMapper.toDTO(seller));
		productDTO.setCategory(CategoryMapper.toDTO(category));
		Product product = ProductMapper.toEntity(productDTO);
		product.setSeller(seller); // Set the seller as the owner of the product
		product.setCategory(category);
		Product savedProduct = sellerDashboardService.addOrUpdateProduct(product, seller);
		logger.info("SellerDashboardController : addOrUpdateProduct SavedProduct {}", savedProduct);
		ProductDTO savedProductdto = ProductMapper.toDTO(savedProduct);
		logger.info("SellerDashboardController : addOrUpdateProduct Response Sent {}", savedProductdto);
		String responseBody = String.format(
				"SellerDashboardController : Product has been added/updated under the User %s %s", username,
				savedProductdto.toString());
		response.setStatus(HttpStatus.OK.value());
		splunkLoggingService.logRequestAndResponse(request, response, responseBody);
		return ResponseEntity.ok("Product added/updated successfully");
	}

	/**
	 * Get all orders associated with the seller's products
	 *
	 * @param authentication the seller's authentication object
	 * @return the list of orders for the seller
	 */
	@GetMapping("/orders")
	public ResponseEntity<?> getSellerOrders(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Check if the user is authenticated and of the right type
		if (authentication != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();

			if (principal instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) principal;

				// Assuming you have a way to fetch a Seller by username or other attributes
				Optional<Seller> sellerOptional = sellerRepository.findByUsername(userDetails.getUsername());
				Seller seller = sellerOptional.orElseThrow(() -> new UsernameNotFoundException("Seller not found"));

				// Now you can use the Seller object
				List<Order> orders = sellerDashboardService.getSellerOrders(seller.getId());
				// Capture request method and URI
				requestMethod = request.getMethod();
				// Set the request method in the request attributes for later use
				request.setAttribute("requestMethod", requestMethod);
				response.setStatus(HttpStatus.OK.value());

				if (orders.isEmpty()) {
					// If the order list is empty, return a specific message
					String responseBody = String.format("No orders for seller with id:  %s", seller.getId());
					splunkLoggingService.logRequestAndResponse(request, response, responseBody);
					return ResponseEntity.ok("No orders for seller with id: " + seller.getId());
				}
				String responseBody = String.format("Seller Dashboard Controller :getSellerOrders %s", orders);
				splunkLoggingService.logRequestAndResponse(request, response, responseBody);
				return ResponseEntity.ok(orders);
			}
		}
		requestMethod = request.getMethod();
		request.setAttribute("requestMethod", requestMethod);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		String responseBody = String.format("User not authenticated or not a seller.");
		splunkLoggingService.logRequestAndResponse(request, response, responseBody);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

	}

	/**
	 * Delete a product based on seller ID and product ID
	 *
	 * @param productId      the ID of the product to delete
	 * @param authentication the seller's authentication object
	 * @return a response indicating the result of the delete operation
	 */
	@DeleteMapping("/deleteProduct/{productId}")
	public ResponseEntity<String> deleteProductBySeller(@PathVariable Long productId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		logger.info("Entered the /Products API");
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			Seller seller = sellerDashboardService.findSellerByUsername(username); // Assuming seller is logged in
			sellerDashboardService.deleteProductBySeller(productId, seller.getId());
			return ResponseEntity.ok("Product deleted successfully");
		}
		logger.info("Unauthorized access attempt.");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

	}
}
