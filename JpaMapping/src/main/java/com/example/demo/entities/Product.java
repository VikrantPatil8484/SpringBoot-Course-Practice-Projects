package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "jpa_product")
public class Product {
	
	@Id
	private String Id;
	
	private String productName;
	
	@ManyToMany(mappedBy="product", cascade=CascadeType.ALL)
	private List<Category> categories = new ArrayList<>();
	
	public Product(String id, String productName) {
		super();
		Id = id;
		this.productName = productName;
	}

	

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}



	public List<Category> getCategories() {
		return categories;
	}



	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

}
