package com.edu.product.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.edu.product.model.entity.Product;

public interface ProductRepo extends R2dbcRepository<Product, Long>{
	
	
}
