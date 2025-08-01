package com.example.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Category;
import com.example.demo.entities.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

//	List<Product> findByTitleContaining(Pageable pageable);

	Page<Product> findByLiveTrue(Pageable pageable);

//	Page<Product> findByTitleContaining(String subTitle, Pageable pageable);
	
	Page<Product> findByTitleContaining(String keyword, Pageable pageable);

	Page<Product> findByCategory(Category category, Pageable pageable);
	
}
