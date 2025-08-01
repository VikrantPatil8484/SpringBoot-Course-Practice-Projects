package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Cart;
import com.example.demo.entities.Users;

public interface CartRepository extends JpaRepository<Cart,String>{
	
	Optional<Cart> findByUser(Users user);

}
