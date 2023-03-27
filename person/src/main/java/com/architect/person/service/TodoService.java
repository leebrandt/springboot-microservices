package com.architect.person.service;

import com.architect.person.dto.Todo;
import com.architect.person.dto.TodoDto;
import com.architect.person.model.Person;
import com.architect.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.List;

import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TodoService {
    static Logger logger = Logger.getLogger(TodoService.class.getName());

    @Value("${todo-microservice}")
    private String todoUrl;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RestTemplate restTemplate;

    final String todoMicroservice = todoUrl + "/api/v1/todo-microservice";

    private Person getPerson(String personName) {
        return personRepository.findByName(personName).orElseThrow(() -> new NoSuchElementException("Person "  + personName + " is not found"));
    }

    public List<Todo> getTodos(Long personId) {
        String urlString = todoUrl + "/api/v1/todo-microservice/todos/person/" + personId;
        logger.log(Level.INFO, "url = "+urlString);

        try {
            URL url = new URL(urlString);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            reader.close();
            connection.disconnect();

            logger.log(Level.INFO, "content = "+content.toString());

            Gson gson = new Gson();
            List<Todo> todos = gson.fromJson(content.toString(), List.class);

            logger.log(Level.INFO, "todos = "+todos);

            return todos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Todo createTodo(TodoDto todoDto) {
        Person person = getPerson(todoDto.getPersonName());
        String urlString = todoUrl + "/api/v1/todo-microservice/todo/person/" + person.getId();
        logger.log(Level.INFO, "url = "+urlString);

        try {
            URL url = new URL(urlString);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            Gson gson = new Gson();
            String json = gson.toJson(todoDto);

            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            connection.getOutputStream().write(input, 0, input.length);

            // Read the response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = reader.readLine()) != null) {
                content.append(inputLine);
            }
            reader.close();
            connection.disconnect();

            logger.log(Level.INFO, "content = "+content.toString());


            Todo todo = gson.fromJson(content.toString(), Todo.class);

            logger.log(Level.INFO, "todo = "+todo);
            return todo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
