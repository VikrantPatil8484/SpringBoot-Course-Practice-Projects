package com.example.demo.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Employee;
import com.example.demo.repository.EmployeeRepo;
import com.example.demo.service.EmployeeService;

//this class will be implementing service Interface here we will writing the method logic tom implement
@Service
public class EmployeeServiceImpl implements EmployeeService{

//	creating reference of our repo class here
	@Autowired
	private EmployeeRepo employeeRepo;
	
	
	@Override
	public Employee addEmployee(Employee employee) {
		Employee emp = employeeRepo.save(employee);
		return emp;
	}

	@Override
	public String removeEmployee(int id) {
		employeeRepo.deleteById(id);
		return "Data deleted successfully";
	}

//	here we will be using optional bcoz by we are finding emp
	// so we can get the employee or not 
	@Override
	public Optional<Employee> findEmployeeById(int id) {
		Optional<Employee> emp = employeeRepo.findById(id);
		
		if(emp.isPresent()) {
			return emp;
		}else {
			return null;
		}
		
	}

	@Override
	public String updateEmployee(int id) {
		Optional<Employee> emp = employeeRepo.findById(id);
		if(emp.isPresent()) {
			Employee emps = new Employee();
			employeeRepo.save(emps);
			return "employee updated successfully";
		}else {
			return "Employee with id not present in db";
		}
		
	}

	@Override
	public List<Employee> getAllEmployee() {
		
		List<Employee> empList = employeeRepo.findAll();
		return empList;
	}

}
