package com.ekart.springekartapplication.DTO;

import lombok.Data;

@Data
public class ResponseDTO<T> {

	private String message;
	private T data;

	public ResponseDTO(String message, T data) {
		super();
		this.message = message;
		this.data = data;
	}

}
