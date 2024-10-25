package com.ekart.springekartapplication.UTIL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ekart.springekartapplication.Service.SplunkLoggingService;

import io.jsonwebtoken.SignatureException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	Logger logger = LogManager.getLogger(JwtRequestFilter.class);

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private SplunkLoggingService splunkLoggingService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");

		String username = null;
		String jwt = null;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7); // Remove "Bearer " prefix
			try {
				username = jwtTokenUtil.extractUsername(jwt);
				logger.info("Extracted Username from JWT: " + username);
			} catch (SignatureException e) {
				logger.error("JWT signature does not match locally computed signature. Invalid JWT token.");
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT signature.");
				String requestMethod = request.getMethod();
				request.setAttribute("requestMethod", requestMethod);
				String responseBody = String
						.format("JWT signature does not match locally computed signature. Invalid JWT token. %s", jwt);
				splunkLoggingService.logRequestAndResponse(request, response, responseBody);
				return; // Stop further processing for this request
			}
		}

		// If the token is valid and the user is not authenticated yet
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			try {
				if (jwtTokenUtil.validateToken(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					logger.info("Token validated successfully for user: " + username);
				}
			} catch (SignatureException e) {
				logger.error("JWT signature does not match locally computed signature. Invalid JWT token.");
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT signature.");
				return; // Stop further processing for this request
			}
		}
		chain.doFilter(request, response);
	}
}
