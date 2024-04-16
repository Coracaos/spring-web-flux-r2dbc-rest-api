package com.edu.product.model.dto;

import lombok.Builder;

@Builder
public class SubErrorDto {
	
	public String field;

	public Object value;

	public String message;

}
