package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.User;


public interface UserRepository extends JpaRepository<User, Integer>{

}


//using crud repo we can not get paging and shorting features