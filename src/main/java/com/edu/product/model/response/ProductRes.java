package com.edu.product.model.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductRes {

	private Long id;
	private String name;
	private String description;
	private Double price;
	private LocalDateTime createAt;
	private LocalDateTime updateAt;

}
