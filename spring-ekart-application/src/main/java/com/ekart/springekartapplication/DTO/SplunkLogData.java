package com.ekart.springekartapplication.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SplunkLogData {
	private String requestUri;
	private String headers;
	private int statusCode;
	private String requestBody;
	private String requestMethod;
	private String txnId;
}
