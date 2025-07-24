package com.example.demo.controllers;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Todo;
import com.example.demo.services.TodoService;

@RestController
@RequestMapping("/todos")
public class TodoController {
	
	Logger logger = LoggerFactory.getLogger(TodoController.class);
	
//	exception handling in spring mvc
//	String str = null;
//	logger.info("{} ", str.length());
	
	
	@Autowired
	private TodoService todoService;
	
//	generating random id
	Random random = new Random();
	
	@PostMapping
	public ResponseEntity<Todo> createTodoHandler(@RequestBody Todo todo) {
		//create todo
		int id = random.nextInt(999999);
		todo.setId(id);
		//create date with syste, default current date
		Date currentDate = new Date();
		logger.info("current Date : {}", currentDate);
		todo.setAddedDate(currentDate);
		logger.info("create todo");
//		call service to create todo
		Todo todo1 = todoService.createTodo(todo);
		return new ResponseEntity<>(todo1, HttpStatus.CREATED);
	}
	
//	get All todo method
	@GetMapping
	public ResponseEntity<List<Todo>> getAllTodoHandler(){
		List<Todo> allTodos = todoService.getAllTodos();
		return new ResponseEntity<>(allTodos, HttpStatus.OK);
	}
	
//	get Single todo method
	@GetMapping("/{todoId}")
	public ResponseEntity<Todo> getSingleTodoHandler(@PathVariable int todoId){
		Todo todo = todoService.getTodo(todoId);
		return ResponseEntity.ok(todo);
	}

//	update todo method
	@PutMapping("/{todoId}")
	public ResponseEntity<Todo> updateTodoHandler(@RequestBody Todo todoWithNewDetails, @PathVariable int todoId){
		Todo todo = todoService.updateTodo(todoId, todoWithNewDetails);
		return ResponseEntity.ok(todo);
	}
	
//	delete todo method
	@DeleteMapping("/{todoId}")
	public ResponseEntity<String> deleteTodo(@PathVariable int todoId) {
		todoService.deleteTodo(todoId);
		return ResponseEntity.ok("todo successfully deleted");
	}
	
//	defining exception handler
//	@ExceptionHandler
//	public String nullPointerExceptionHandler(NullPointerException e) {
//		System.out.println(e.getMessage());
//		System.out.println("null pointer exception generated");
//		return "null pointer exception generated" + e.getMessage();
//	}
}
