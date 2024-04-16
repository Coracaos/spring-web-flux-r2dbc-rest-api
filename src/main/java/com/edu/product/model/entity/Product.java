package com.edu.product.model.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Table("product")
@Getter
@Setter
@Builder
public class Product {
	
	@Id
	@Column("product_id")
	private Long id;

	@Column("name")
	private String name;

	@Column("description")
	private String description;

	@Column("price")
	private Double price;

	@Column("created_at")
	private LocalDateTime createdAt;

	@Column("updated_at")
	private LocalDateTime updatedAt;

}
