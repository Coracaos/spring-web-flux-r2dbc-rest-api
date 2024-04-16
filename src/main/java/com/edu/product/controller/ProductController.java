package com.edu.product.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.product.model.request.ProductReq;
import com.edu.product.model.response.ErrorRes;
import com.edu.product.model.response.ProductRes;
import com.edu.product.model.response.ProductsWrapRes;
import com.edu.product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@Operation(summary = "Get all the products")
	@ApiResponses(
		@ApiResponse(responseCode = "200", description = "All the products",
		content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductsWrapRes.class))})
	)
	@GetMapping(value = "/products")
	public Mono<ResponseEntity<ProductsWrapRes>> getProducts() {
		return productService.getProductsWrapRes()
				.map(p -> ResponseEntity.ok().body(p));
	}

	@Operation(summary = "Get a product by id")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Get a product",
		content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductRes.class))}),
		@ApiResponse(responseCode = "404", description = "Product not found", 
		content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class))}),
		@ApiResponse(responseCode = "400", description = "Invalid id supplied", 
		content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class))}),
		}
	)
	@GetMapping(value = "/products/{id}")
	public Mono<ResponseEntity<ProductRes>> getProduct(@PathVariable("id") Long id) {
		return productService.getProductRes(id)
				.map(p -> ResponseEntity.ok().body(p));
	}

	@Operation(summary = "Create a product")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Product created", 
		headers = {@Header(name = "Location", description = "Product id")}),
		@ApiResponse(responseCode = "400", description = "Invalid parameters supplied", 
		content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class))}),
		}
	)
	@PostMapping(value = "/products")
	public Mono<ResponseEntity<Void>> createProduct(@Valid @RequestBody ProductReq productReq) {
		return productService.createProduct(productReq)
				.map(p -> ResponseEntity.created(URI.create(p.toString())).build());
	}

	@Operation(summary = "Update a product by id")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Product updated"),
		@ApiResponse(responseCode = "400", description = "Invalid parameters supplied", 
		content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class))}),
		}
	)
	@PutMapping(value = "/products/{id}")
	public Mono<ResponseEntity<Void>> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductReq productReq){
		return productService.updateProduct(id, productReq)
				.map(p -> ResponseEntity.noContent().build());
	}

	@Operation(summary = "Delete a product by id")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Product deleted"),
		@ApiResponse(responseCode = "400", description = "Invalid id supplied", 
		content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class))}),
		}
	)
	@DeleteMapping(value = "/products/{id}")
	public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable("id") Long id){
		return productService.deleteProduct(id)
				.map(p -> ResponseEntity.noContent().build());
	}
	
}
