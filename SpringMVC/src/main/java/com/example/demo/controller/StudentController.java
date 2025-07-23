package com.example.demo.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Student;

@RestController
@RequestMapping("/student")
public class StudentController {
//	create student - data : required
//  we are receiving data in json format
//	JSon -- Java object -> called deserialization
//	Java object -- JSON -> serializ
//	@PostMapping("/create")
//	public void createStudent(@RequestBody Map<String, Object> data) {
////		we have to create student here
//		System.out.println(data);
//		Object name = data.get("name");
//		Object empId = data.get("empId");
//		System.out.println("emp Id "+ empId);
//		
//	}
	
	@PostMapping("/create")
	public Map<String, Object> createStudent(@RequestBody Student student) {
//		System.out.println(student.size());
		System.out.println(student);
		
		Map<String, Object> data = new HashMap<>();
		data.put("content", student);
		data.put("error", "No error found");
		data.put("currentData", new Date());
		data.put("SystemInformation", System.getProperties());
		
		return data;
//		ResponseEntity<Student> response = new ResponseEntity<>(student, HttpStatus.CREATED);
		
	}

}
