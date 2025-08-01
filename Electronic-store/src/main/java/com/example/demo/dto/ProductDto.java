package com.example.demo.dto;

import java.util.Date;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	private String productId;
	private String productImageName;

	
	private String title;
	@Column(length = 10000)
	private String description;

	private int price;

	
	private int discountedPrice;
	private int quantity;
	private Date addedDate;

	private boolean live;
	

	private boolean stock;
	private CategoryDto category;

	

}
