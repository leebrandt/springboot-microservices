package com.architect.todo.service;

import com.architect.todo.dto.TodoDto;
import com.architect.todo.model.Todo;
import com.architect.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getTodos(Long personId) {
        return todoRepository.findByPersonId(personId);
    }

    public Todo createTodo(Long personId, TodoDto todoDto) {
        return todoRepository.save(Todo.builder().personId(personId).task(todoDto.getTask()).build());
    }

}
