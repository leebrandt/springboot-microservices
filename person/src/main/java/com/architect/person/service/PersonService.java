package com.architect.person.service;

import com.architect.person.dto.PersonDto;
import com.architect.person.model.Person;
import com.architect.person.repository.PersonRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public String createPerson(PersonDto personDto) {
        Optional<Person> personOptional = this.personRepository.findByName(personDto.getName());
        if (personOptional.isPresent()) {
            throw new EntityExistsException("Person already exists.");
        }
        Person person = this.personRepository.save(Person.builder().name(personDto.getName()).build());
        return "Successfully created " + person.getName();
    }
}
