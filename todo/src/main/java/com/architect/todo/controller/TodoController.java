package com.architect.todo.controller;

import com.architect.todo.model.Todo;
import com.architect.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo-microservice/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("{personId}")
    public ResponseEntity<List<Todo>> getTodos(@PathVariable("personId") Long personId) {
        return ResponseEntity.ok(todoService.getTodos(personId));
    }
}
