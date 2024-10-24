package com.ekart.springekartapplication.UTIL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ekart.springekartapplication.Service.SplunkLoggingService;

@Component
public class LoggingFilter implements Filter {

	@Autowired
	private SplunkLoggingService splunkLoggingService;

	public LoggingFilter(SplunkLoggingService splunkLoggingService) {
		this.splunkLoggingService = splunkLoggingService;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Initialization logic, if necessary
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// Capture request method and URI
		String requestMethod = httpRequest.getMethod();
		String requestUri = httpRequest.getRequestURI();

		// Set the request method in the request attributes for later use
		httpRequest.setAttribute("requestMethod", requestMethod);

		// Log the request details
		splunkLoggingService.logRequestAndResponse(httpRequest, httpResponse,
				"Incoming Request: " + requestMethod + " " + requestUri);

		// Continue with the filter chain (proceed with the request)
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// Cleanup logic, if necessary
	}
}
