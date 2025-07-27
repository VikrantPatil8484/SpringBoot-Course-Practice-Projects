package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer>{

}

// controller layer is - presentation part
// repository class or layer - database layer from here database is access
// service class or layer - business logic layer 