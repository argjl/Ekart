package com.ekart.springekartapplication.Auth;

import com.ekart.springekartapplication.DTO.AuthRequest;
import com.ekart.springekartapplication.DTO.AuthResponse;
import com.ekart.springekartapplication.UTIL.JwtTokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil; // Utility to generate JWT tokens

//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
//		logger.info("Login attempt for user: {}", authRequest.getUsername());
//		try {
//			// Authenticate the user
//			Authentication authentication = authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//			logger.info("Authentication successful for user: {}", authRequest.getUsername());
//			// Load user details
//			UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
//			logger.info("User roles: {}", userDetails.getAuthorities());
//			// Generate JWT token
//			String token = jwtTokenUtil.generateToken(userDetails);
//
//			// Return the token in the response
//			SecurityContextHolder.getContext().setAuthentication(authentication);
//			return ResponseEntity.ok(new AuthResponse(token));
//		} catch (AuthenticationException e) {
//			logger.error("Authentication failed for user: {}", authRequest.getUsername(), e);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//		}
//	}

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
		logger.info("Login attempt for user: {}", authRequest.getUsername());
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect Username and Password", e);
		}
		logger.info("Authentication successful for user: {}", authRequest.getUsername());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		logger.info("Toekn Genrated successful for user: {}, Token {}", authRequest.getUsername(), jwt);
		return ResponseEntity.ok(new AuthResponse(jwt));
	}

	@GetMapping("/test")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok("Test endpoint works!");
	}
}
