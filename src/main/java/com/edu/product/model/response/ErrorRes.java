package com.edu.product.model.response;

import java.util.List;

import com.edu.product.model.dto.SubErrorDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorRes {

	private int code;
	private String message;
	private List<SubErrorDto> subErrors;

}
