package com.architect.person.controller;

import com.architect.person.dto.PersonDto;
import com.architect.person.dto.TodoDto;
import com.architect.person.service.PersonService;
import com.architect.person.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class PersonController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private TodoService todoService;

    @Autowired
    private PersonService personService;

    @PostMapping("/api/v1/person-microservice")
    public ResponseEntity createPerson(@RequestBody PersonDto personDto) {
        try {
            return ResponseEntity.ok(personService.createPerson(personDto));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Failed to create Person");
        }
    }

    @GetMapping("/api/v1/person-microservice/todos")
    public ResponseEntity getTodos(@RequestBody String personName) {
        try {
            return ResponseEntity.ok(todoService.getTodos(personName));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Either person is not found or there are no todos for " + personName);
        }
    }

    @PostMapping("/api/v1/person-microservice/todo")
    public ResponseEntity createTodo( @RequestBody TodoDto todoDto) {
        try {
            return ResponseEntity.ok(todoService.createTodo(todoDto));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Failed to create Todo. Please make sure " + todoDto.getPersonName() + " exists.");
        }
    }
}
