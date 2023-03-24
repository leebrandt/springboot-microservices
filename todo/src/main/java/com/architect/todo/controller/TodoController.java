package com.architect.todo.controller;

import com.architect.todo.dto.TodoDto;
import com.architect.todo.model.Todo;
import com.architect.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo-microservice")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/todos/person/{personId}")
    public ResponseEntity<List<Todo>> getTodos(@PathVariable("personId") Long personId) {
        return ResponseEntity.ok(todoService.getTodos(personId));
    }

    @PostMapping("/todo/person/{personId}")
    public ResponseEntity createTodo(@PathVariable("personId") Long personId, @RequestBody TodoDto todoDto) {
        return ResponseEntity.ok(todoService.createTodo(personId, todoDto));
    }
}
