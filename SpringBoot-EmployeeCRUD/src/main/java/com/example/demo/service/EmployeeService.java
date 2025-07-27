package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Employee;

public interface EmployeeService {
	
//	to add employee method
	public Employee addEmployee(Employee employee);
	
//	to remove  employee method
	public String removeEmployee(int id);
	
//	to find employee byt id method
	public Optional<Employee> findEmployeeById(int id);
	
//	to update employee method
	public String updateEmployee(int id);
	
//	to get list of all employees
	public List<Employee> getAllEmployee();
}
