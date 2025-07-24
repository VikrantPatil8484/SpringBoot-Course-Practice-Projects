package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo.models.Todo;

@Component
public class TodoService {
	
	Logger logger = LoggerFactory.getLogger(TodoService.class);

//	creating fake data as we are not using any db here
	List<Todo> todos = new ArrayList<>();

//	creae todo method
	public Todo createTodo(Todo todo) {
//		create..
		// change the logic
		todos.add(todo);
		logger.info("Todos {} ", this.todos);
		return todo;
	}

	public List<Todo> getAllTodos() {
		// TODO Auto-generated method stub
		return todos;
	}
	
	public Todo getTodo(int todoId) {
		Todo todo = todos.stream().filter(t->todoId==t.getId()).findAny().get();
		logger.info("todo : {}", todo);
		return todo;
	}
	
	public Todo updateTodo(int todoId, Todo todo) {
		List<Todo> newUpdateList = todos.stream().map(t->{
			if(t.getId()==todoId) {
//				perform update
				t.setTitle(todo.getTitle());
				t.setContent(todo.getContent());
				t.setStatus(todo.getStatus());
				return t;
			}else {
				return t;
			}
		}).collect(Collectors.toList());
		
		todos=newUpdateList;
		todo.setId(todoId);
		return todo;
		
	}

	public void deleteTodo(int todoId) {
		// TODO Auto-generated method stub
		List<Todo> newList = todos.stream().filter(t->t.getId() != todoId).collect(Collectors.toList());
		todos = newList;
		
	}

}
