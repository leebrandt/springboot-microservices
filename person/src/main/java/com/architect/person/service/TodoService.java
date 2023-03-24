package com.architect.person.service;

import com.architect.person.dto.Todo;
import com.architect.person.dto.TodoDto;
import com.architect.person.model.Person;
import com.architect.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import java.util.NoSuchElementException;

@Service
public class TodoService {

    @Value("${todo-microservice-protocol}")
    private String todoMicroserviceProtocol;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RestTemplate restTemplate;

    final String todoMicroservice = "%s://todo-client/api/v1/todo-microservice";

    private Person getPerson(String personName) {
        return personRepository.findByName(personName).orElseThrow(() -> new NoSuchElementException("Person "  + personName + " is not found"));
    }

    public List<Todo> getTodos(String personName) {
        Person person = getPerson(personName);
        ResponseEntity<Todo[]> response = restTemplate.getForEntity(
                String.format(todoMicroservice, todoMicroserviceProtocol) + "/todos/" + person.getId(),
                Todo[].class
        );
        return List.of(response.getBody());
    }

    public Todo createTodo(TodoDto todoDto) {
        Person person = getPerson(todoDto.getPersonName());
        String url = String.format(todoMicroservice, todoMicroserviceProtocol) + "/todo/person/" + person.getId();
        ResponseEntity<Todo> response = restTemplate.postForEntity(url, todoDto, Todo.class);
        return response.getBody();
    }
}
