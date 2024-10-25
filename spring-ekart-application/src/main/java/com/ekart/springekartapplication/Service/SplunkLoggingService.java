//Code for Splunk logging without Lombok Builder fucntion

//package com.ekart.springekartapplication.Service;
//
//import org.apache.hc.client5.http.classic.methods.HttpPost;
//import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
//import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
//import org.apache.hc.client5.http.impl.classic.HttpClients;
//import org.apache.hc.core5.http.io.entity.StringEntity;
//import org.apache.hc.core5.http.ContentType;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.core.io.support.PropertiesLoaderUtils;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Enumeration;
//import java.util.Properties;
//import java.util.UUID;
//
//@Service
//public class SplunkLoggingService {
//
//	private static final Logger logger = LogManager.getLogger(SplunkLoggingService.class);
//	private static String splunkUrl;
//	private static String splunkToken;
//	private static String sourcetype;
//	private static String index;
//
//	static {
//		try {
//			Properties props = PropertiesLoaderUtils.loadAllProperties("splunk.properties");
//			splunkUrl = props.getProperty("splunk.url");
//			splunkToken = props.getProperty("splunk.token");
//			sourcetype = props.getProperty("splunk.sourcetype");
//			index = props.getProperty("splunk.index");
//		} catch (Exception e) {
//			logger.error("Error loading Splunk configuration: {}", e.getMessage());
//		}
//	}
//
//	public void logRequestAndResponse(HttpServletRequest request, HttpServletResponse response, String responseBody) {
//		String txnId = getOrGenerateTxnId(request); // Get or generate the txnId
//
//		String requestURI = request.getRequestURI();
//		int statusCode = response.getStatus();
//		String headers = extractHeaders(request);
//
//		// Log request and response details to console using Log4j
//		logger.info("[TxnID: {}] Request URI: {}", txnId, requestURI);
//		logger.info("[TxnID: {}] Request Headers: {}", txnId, headers);
//		logger.info("[TxnID: {}] Response Status Code: {}", txnId, statusCode);
//		logger.info("[TxnID: {}] Response Body: {}", txnId, responseBody);
//
//		// Send log to Splunk
//		sendLogToSplunk("Request processed", requestURI, headers, statusCode, responseBody, txnId);
//	}
//
//	private String getOrGenerateTxnId(HttpServletRequest request) {
//		// Check if txnId exists in request attributes
//		String txnId = (String) request.getAttribute("txnId");
//		if (txnId == null) {
//			txnId = UUID.randomUUID().toString(); // Generate new txnId
//			request.setAttribute("txnId", txnId); // Store txnId in request for future use
//		}
//		return txnId;
//	}
//
//	private void sendLogToSplunk(String message, String requestURI, String headers, int statusCode, String responseBody,
//			String txnId) {
//		try (CloseableHttpClient client = HttpClients.createDefault()) {
//			HttpPost post = new HttpPost(splunkUrl);
//			post.setHeader("Authorization", "Splunk " + splunkToken);
//			post.setHeader("Content-Type", "application/json");
//
//			String jsonPayload = String.format(
//					"{ \"sourcetype\": \"%s\", \"index\": \"%s\", \"event\": { \"txnId\": \"%s\", \"message\": \"%s\", \"requestURI\": \"%s\", \"headers\": \"%s\", \"statusCode\": \"%d\", \"response\": \"%s\" } }",
//					sourcetype, index, txnId, message, requestURI, headers, statusCode, responseBody);
//
//			post.setEntity(new StringEntity(jsonPayload, ContentType.APPLICATION_JSON));
//
//			try (CloseableHttpResponse splunkResponse = client.execute(post)) {
//				logger.info("[TxnID: {}] Sent log to Splunk: {} - {}", txnId, splunkResponse.getCode(),
//						splunkResponse.getReasonPhrase());
//			}
//		} catch (IOException e) {
//			logger.error("[TxnID: {}] Error sending log to Splunk: {}", txnId, e.getMessage());
//		}
//	}
//
//	private String extractHeaders(HttpServletRequest request) {
//		Enumeration<String> headerNames = request.getHeaderNames();
//		StringBuilder headers = new StringBuilder();
//
//		if (headerNames != null) {
//			while (headerNames.hasMoreElements()) {
//				String headerName = headerNames.nextElement();
//				String headerValue = request.getHeader(headerName);
//				headers.append(headerName).append(": ").append(headerValue).append(", ");
//			}
//		}
//
//		if (headers.length() > 0) {
//			headers.setLength(headers.length() - 2); // Remove the last comma and space if present
//		}
//
//		return headers.toString();
//	}
//}

// Code for Splunk Logging using the Builders function

package com.ekart.springekartapplication.Service;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import com.ekart.springekartapplication.DTO.SplunkLogData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.UUID;


@Service
public class SplunkLoggingService {

	private static final Logger logger = LogManager.getLogger(SplunkLoggingService.class);
	private static String splunkUrl;
	private static String splunkToken;
	private static String sourcetype;
	private static String index;

	static {
		try {
			Properties props = PropertiesLoaderUtils.loadAllProperties("splunk.properties");
			splunkUrl = props.getProperty("splunk.url");
			splunkToken = props.getProperty("splunk.token");
			sourcetype = props.getProperty("splunk.sourcetype");
			index = props.getProperty("splunk.index");
		} catch (Exception e) {
			logger.error("Error loading Splunk configuration: {}", e.getMessage());
		}
	}

	public void logRequestAndResponse(HttpServletRequest request, HttpServletResponse response, String responseBody) {
		String txnId = getOrGenerateTxnId(request); // Get or generate the txnId
		String requestMethod = (String) request.getAttribute("requestMethod");
		SplunkLogData logData = SplunkLogData.builder().requestUri(request.getRequestURI())
				.headers(extractHeaders(request)).statusCode(response.getStatus()).requestBody(responseBody)
				.txnId(txnId).requestMethod(requestMethod).build();

		// Log to console using Log4j
		logToConsole(logData);

		// Send log to Splunk
		sendLogToSplunk(logData);
	}

	private void logToConsole(SplunkLogData logData) {
		logger.info("[TxnID: {}] Request URI: {}", logData.getTxnId(), logData.getRequestUri());
		logger.info("[TxnID: {}] Request Headers: {}", logData.getTxnId(), logData.getHeaders());
		logger.info("[TxnID: {}] Status Code: {}", logData.getTxnId(), logData.getStatusCode());
		logger.info("[TxnID: {}] Request Method: {}", logData.getTxnId(), logData.getRequestMethod());
		logger.info("[TxnID: {}] Response Body: {}", logData.getTxnId(), logData.getRequestBody());
	}

	private void sendLogToSplunk(SplunkLogData logData) {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPost post = new HttpPost(splunkUrl);
			post.setHeader("Authorization", "Splunk " + splunkToken);
			post.setHeader("Content-Type", "application/json");

			String jsonPayload = String.format(
					"{ \"sourcetype\": \"%s\", \"index\": \"%s\", \"event\": { \"txnId\": \"%s\", \"requestURI\": \"%s\", \"headers\": \"%s\",\"requestMethod\": \"%s\", \"statusCode\": \"%d\", \"response\": \"%s\" } }",
					sourcetype, index, logData.getTxnId(), logData.getRequestUri(), logData.getHeaders(),logData.getRequestMethod(),
					logData.getStatusCode(), logData.getRequestBody());

			post.setEntity(new StringEntity(jsonPayload, ContentType.APPLICATION_JSON));

			try (CloseableHttpResponse splunkResponse = client.execute(post)) {
				logger.info("[TxnID: {}] Sent log to Splunk: {} - {}", logData.getTxnId(), splunkResponse.getCode(),
						splunkResponse.getReasonPhrase());
			}
		} catch (IOException e) {
			logger.error("[TxnID: {}] Error sending log to Splunk: {}", logData.getTxnId(), e.getMessage());
		}
	}

	private String getOrGenerateTxnId(HttpServletRequest request) {
		// Check if txnId exists in request attributes
		String txnId = (String) request.getAttribute("txnId");
		if (txnId == null) {
			txnId = UUID.randomUUID().toString(); // Generate new txnId
			request.setAttribute("txnId", txnId); // Store txnId in request for future use
		}
		return txnId;
	}

	private String extractHeaders(HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();
		StringBuilder headers = new StringBuilder();

		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				String headerValue = request.getHeader(headerName);
				headers.append(headerName).append(": ").append(headerValue).append(", ");
			}
		}

		// Remove trailing comma and space
		if (headers.length() > 0) {
			headers.setLength(headers.length() - 2);
		}

		return headers.toString();
	}
}
