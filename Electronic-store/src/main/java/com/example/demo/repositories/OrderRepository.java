package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Order;
import com.example.demo.entities.Users;

public interface OrderRepository extends JpaRepository<Order, String>{

	//custom finder method
	List<Order> findByUser(Users user);
}


//if order is removed orderitem will also be removed