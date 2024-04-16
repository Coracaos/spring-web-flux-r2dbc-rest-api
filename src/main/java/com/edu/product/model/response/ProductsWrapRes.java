package com.edu.product.model.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductsWrapRes {

	private List<ProductRes> products;

}
