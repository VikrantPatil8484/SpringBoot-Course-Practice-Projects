package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Product;

public interface ProductRepo extends JpaRepository<Product, String> {

//	1. Custome method finders as per the requorements
//	Rule

	List<Product> findByProductName(String productName);

	Product findById(int id);

	Product findByProductNameIs(String productName);

	Product findByProductNameIsNot(String productName);

	List<Product> findByProductNameIsNull();

	List<Product> findByProductNameIsNotNull();

	List<Product> findByActiveTrue();

//	findByFieldnameincapital 
	List<Product> findByProductNameStartingWith(String prefix);

	String pattern = "Samsung";

	List<Product> findByProductNameLike(String pattern);

	// 2. Find products whose name contains a keyword (case insensitive)
	List<Product> findByProductNameContainingIgnoreCase(String keyword);

	// 3. Find products by category name using JPQL
	@Query("SELECT p FROM Product p JOIN p.categories c WHERE c.categoryName = ?1")
	List<Product> findProductsByCategoryName(String categoryName);

	// 4. Find all products having more than one category
	@Query("SELECT p FROM Product p WHERE SIZE(p.categories) > 1")
	List<Product> findProductsWithMultipleCategories();

	// 5. Count how many products have a certain name
	long countByProductName(String productName);
}
