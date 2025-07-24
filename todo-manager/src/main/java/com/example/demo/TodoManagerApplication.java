package com.example.demo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.dao.TodoDao;
import com.example.demo.models.Todo;

@SpringBootApplication
public class TodoManagerApplication implements CommandLineRunner {

    @Autowired
    private TodoDao todoDao;
    
    Logger logger = LoggerFactory.getLogger(TodoDao.class);
	

    public static void main(String[] args) {
        SpringApplication.run(TodoManagerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Application startup logic here (optional)
        // For example:
        // System.out.println("App started!");
        // JdbcTemplate template = todoDao.getTemplate();
    	
//    	Todo todo = new Todo();
//    	todo.setId(123);
//    	todo.setTitle("this is testing spring jdbc");
//    	todo.setContent("Wow its working");
//    	todo.setStatus("pending");
//    	todo.setAddedDate(new Date());
//    	todo.setToDoDate(new Date());
//    	
//    	todoDao.saveTodo(todo);
    	
//    	Todo todo = todoDao.getTodo(123);
//    	logger.info("TODO : {}", todo);
    	
    	List<Todo> allTodos = todoDao.getAllTodos();
//    	logger.info("All todos : {}", allTodos);
    	System.out.println("ALL todos : " + allTodos);
    }
}