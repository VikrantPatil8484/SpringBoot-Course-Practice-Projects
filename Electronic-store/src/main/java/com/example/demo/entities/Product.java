package com.example.demo.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	private String productId;
	
	private String title;
	@Column(length = 10000)
	private String description;

	private int price;
	
	private int discountedPrice;
	private int quantity;
	private Date addedDate;

	private boolean live;
	
	private boolean stock;
	private String productImageName;
	
	
	//one product is mapped to single category - when we fetch product we can get categort so using eager here
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="category_id")
	private Category category;
	
	

}
