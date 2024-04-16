package com.edu.product.model.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductReq {

	@NotBlank
	@Size(min = 3, max = 80)
	private String name;

	@Size(min = 0, max = 2000)
	private String description;

	@DecimalMin("1.0")
	private Double price;

}
