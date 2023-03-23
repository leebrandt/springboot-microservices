package com.architect.person.service;

import com.architect.person.dto.Todo;
import com.architect.person.model.Person;
import com.architect.person.repository.PersonRepository;
import jakarta.ws.rs.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TodoService {
  @Value("${todo-microservice-protocol}")
  private String todoMicroserviceProtocol;

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private RestTemplate restTemplate;

  final String todoMicroservice = "%s://todo-client/api/v1/todo-microservice";

  public List<Todo> getTodos(String personName) {
    Person person = personRepository.findByName(personName).orElseThrow(ForbiddenException::new);
    ResponseEntity<Todo[]> response = restTemplate.getForEntity(
        String.format(todoMicroservice, todoMicroserviceProtocol) + "/todos/" + person.getId(),
        Todo[].class
    );
    return List.of(response.getBody());
  }

  public void save(Person person) {
    this.personRepository.save(person);
  }
}
