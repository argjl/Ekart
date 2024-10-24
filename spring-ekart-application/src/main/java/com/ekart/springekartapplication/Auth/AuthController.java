package com.ekart.springekartapplication.Auth;

import com.ekart.springekartapplication.DTO.AuthRequest;
import com.ekart.springekartapplication.DTO.AuthResponse;
import com.ekart.springekartapplication.Service.SplunkLoggingService;
import com.ekart.springekartapplication.UTIL.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private static final Logger logger = LogManager.getLogger(AuthController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private SplunkLoggingService splunkLoggingService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil; // Utility to generate JWT tokens

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Login attempt for user: {}", authRequest.getUsername());

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (BadCredentialsException e) {
			logger.error("Failed login attempt for user: {}", authRequest.getUsername());
			splunkLoggingService.logRequestAndResponse(request, response, "Authentication failed: Invalid credentials");
			throw new Exception("Incorrect Username and Password", e);
		}

		logger.info("Authentication successful for user: {}", authRequest.getUsername());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
		// Generate Token
		final String jwt = jwtTokenUtil.generateToken(userDetails);

		logger.info("Token generated successfully for user: {}", authRequest.getUsername());
		splunkLoggingService.logRequestAndResponse(request, response,
				" Auth Response Token Genrated Successfully: " + jwt);
		// Return the token in the response
		return ResponseEntity.ok(new AuthResponse(jwt));
	}

}