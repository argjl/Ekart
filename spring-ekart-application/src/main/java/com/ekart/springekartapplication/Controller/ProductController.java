package com.ekart.springekartapplication.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ekart.springekartapplication.Service.SplunkLoggingService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private Gson gson;

	@Autowired
	private SplunkLoggingService splunkLoggingService;

	String logMessage;

	@PostMapping("/addProduct")
	public ResponseEntity<String> addProduct(@RequestBody ProductDTO product,HttpServletRequest request, HttpServletResponse response) {
		 String requestBody = String.format("ProductController : addProduct Request %s",product.toString());
         splunkLoggingService.logRequestAndResponse(request, response, requestBody);
		Product saveProduct = productService.addProduct(product);
		String jsonResponse = gson.toJson(new ResponseDTO<>("Product has been Added Succesfully", saveProduct));
		String responseBody = String.format("ProductController : addProduct Response Product has been added %s",jsonResponse);
        splunkLoggingService.logRequestAndResponse(request, response, responseBody);
		return ResponseEntity.ok(jsonResponse);
	}

	@PostMapping("/updateProduct")
	public ResponseEntity<String> updateProduct(@RequestBody ProductDTO productDTO,HttpServletRequest request, HttpServletResponse response) {
		String requestBody = String.format("ProductController : updateProduct Request %s",productDTO.toString());
        splunkLoggingService.logRequestAndResponse(request, response, requestBody);
		Product saveProduct = productService.updateProduct(productDTO);
		String jsonResponse = gson.toJson(new ResponseDTO<>("Product has been updated Succesfully", saveProduct));
		String responseBody = String.format("ProductController : updateProduct Response Product has been updated %s",jsonResponse);
        splunkLoggingService.logRequestAndResponse(request, response, responseBody);
		return ResponseEntity.ok(jsonResponse);
	}

	@GetMapping("/view")
	public ResponseEntity<List<Product>> getAllProducts(HttpServletRequest request, HttpServletResponse response) {
		String requestBody = String.format("ProductController : getAllProducts Request ");
        splunkLoggingService.logRequestAndResponse(request, response, requestBody);
		List<Product> products = productService.getAllProducts();
		String responseBody = String.format("ProductController : getAllProducts Response All Product %s",products);
        splunkLoggingService.logRequestAndResponse(request, response, responseBody);
		return ResponseEntity.ok(products);

	}

	@GetMapping("/search")
	public List<Product> searchProducts(@RequestParam String name, HttpServletRequest request, HttpServletResponse response) {
		String requestBody = String.format("ProductController : searchProducts Request Searching the Products with name \"%s\" ",name);
        splunkLoggingService.logRequestAndResponse(request, response, requestBody);
        
        String responseBody = String.format("ProductController : searchProducts Response Products with name %s %s",name, productService.searchProducts(name).toString());
        splunkLoggingService.logRequestAndResponse(request, response, responseBody);
		return productService.searchProducts(name);
	}

	@GetMapping("/filter")
	public List<Product> filterProducts(@RequestParam Long categoryId, @RequestParam Long minimumPrice,
			@RequestParam Long maximumPrice,HttpServletRequest request, HttpServletResponse response) {
		String requestBody = String.format("ProductController : filterProducts Request Filtering the Products with CategoryId MinimumPrice Maximum Price %s %s %s ",categoryId, minimumPrice, maximumPrice);
        splunkLoggingService.logRequestAndResponse(request, response, requestBody);
        
        String responseBody = String.format("ProductController : filterProducts Response Filtered Products with CategoryId MinimumPrice Maximum Price %s ",productService.filterProducts(categoryId, minimumPrice, maximumPrice).toString());
        splunkLoggingService.logRequestAndResponse(request, response, responseBody);
		return productService.filterProducts(categoryId, minimumPrice, maximumPrice);
	}

//	@DeleteMapping("/delete/{productId}")
//	public ResponseEntity<List<Product>> deleteProducts(@PathVariable Long productId) {
//		List<Product> updatedProducts=productService.deleteProduct(productId);
//		return ResponseEntity.ok(updatedProducts);
//	}

}
