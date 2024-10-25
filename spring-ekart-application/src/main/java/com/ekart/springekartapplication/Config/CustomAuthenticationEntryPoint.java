package com.ekart.springekartapplication.Config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.ekart.springekartapplication.Service.SplunkLoggingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	private SplunkLoggingService splunkLoggingService;

	Logger logger = LogManager.getLogger(CustomAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		// Log unauthenticated access attempt

		// Capture request method and URI
		String requestMethod = request.getMethod();
		// Set the request method in the request attributes for later use
		request.setAttribute("requestMethod", requestMethod);
		logger.error("Unauthorized access attempt: Unauthorized");

		// Respond with a 401 status and custom message
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
		String responseBody = String.format("Unauthorized access attempt: Unauthorized %s ",
				authException.getMessage());
		splunkLoggingService.logRequestAndResponse(request, response, responseBody);

	}
}
