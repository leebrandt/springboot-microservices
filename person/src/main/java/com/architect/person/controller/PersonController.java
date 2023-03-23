package com.architect.person.controller;

import com.architect.person.dto.Todo;
import com.architect.person.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/")
public class PersonController {

  @Autowired
  private DiscoveryClient discoveryClient;

  @Autowired
  private TodoService todoService;

  @GetMapping
  public ModelAndView home() {
    ModelAndView modelAndView = new ModelAndView("index");
    modelAndView.addObject("todos", todoService.getTodos("Rex"));
    return modelAndView;
  }

  @GetMapping("/health")
  public ResponseEntity health() {
    return ResponseEntity.ok("Success");
  }

  @GetMapping("/api/v1/person-microservice/todos")
  public List<Todo> getTodo() {
    return todoService.getTodos("Rex");
  }
}
