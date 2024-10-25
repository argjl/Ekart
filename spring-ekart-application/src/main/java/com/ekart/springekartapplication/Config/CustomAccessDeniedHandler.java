package com.ekart.springekartapplication.Config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.ekart.springekartapplication.Service.SplunkLoggingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	Logger logger = LogManager.getLogger(CustomAccessDeniedHandler.class);

	@Autowired
	private SplunkLoggingService splunkLoggingService;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException {
		// Log the access denied message (you can replace with a logger)
		logger.error("Unauthorized access attempt: " + accessDeniedException.getMessage());

		// Set the response status and message
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getWriter().write("Access Denied. You do not have the necessary permissions.");
		response.getWriter().flush();
		String responseBody = String.format("Unauthorized access attempt: %s ", accessDeniedException.getMessage());
		splunkLoggingService.logRequestAndResponse(request, response, responseBody);
	}
}
