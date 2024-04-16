package com.edu.product.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.edu.product.exception.NotFoundResourceException;
import com.edu.product.model.entity.Product;
import com.edu.product.model.request.ProductReq;
import com.edu.product.model.response.ProductRes;
import com.edu.product.model.response.ProductsWrapRes;
import com.edu.product.repository.ProductRepo;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepo productRepo;

	public Mono<ProductsWrapRes> getProductsWrapRes() {
		return productRepo.findAll()
			   .map(this::getProductRes)
			   .collectList()
			   .map(this::getProductsWrapResp);
	}

	public Mono<ProductRes> getProductRes(Long id){
		return productRepo.findById(id)
			   .switchIfEmpty(Mono.error(new NotFoundResourceException("product not found")))
			   .map(this::getProductRes);
	}

	public Mono<Long> createProduct(ProductReq productReq){
		return Mono.just(productReq)
			   .map(p -> Product.builder()
					   .name(p.getName())
					   .description(p.getDescription())
					   .price(p.getPrice())
					   .createdAt(LocalDateTime.now())
					   .build())
			   .flatMap(productRepo::save)
			   .map(p -> p.getId());
	}

	public Mono<Void> updateProduct(Long id, ProductReq productReq){
		return productRepo.findById(id)
			   .switchIfEmpty(Mono.error(new NotFoundResourceException("product not found")))
			   .map(p -> Product.builder()
					   .id(id)
					   .name(productReq.getName())
					   .description(productReq.getDescription())
					   .price(productReq.getPrice())
					   .createdAt(p.getCreatedAt())
					   .updatedAt(LocalDateTime.now())
					   .build())
			   .flatMap(productRepo::save)
			   .then();
			   
	}

	public Mono<Void> deleteProduct(Long id){
		return productRepo.deleteById(id);
	}

	private ProductRes getProductRes(Product product){
		return ProductRes.builder()
			   .id(product.getId())
			   .name(product.getName())
			   .description(product.getDescription())
			   .price(product.getPrice())
			   .createAt(product.getCreatedAt())
			   .updateAt(product.getUpdatedAt())
			   .build();
	}

	private ProductsWrapRes getProductsWrapResp(List<ProductRes> products){
		return ProductsWrapRes.builder().products(products).build();
	}

}
