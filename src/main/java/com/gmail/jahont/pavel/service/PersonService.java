package com.gmail.jahont.pavel.service;

import java.util.List;

import com.gmail.jahont.pavel.repository.model.Person;
import com.gmail.jahont.pavel.service.model.AddPersonDTO;
import com.gmail.jahont.pavel.service.model.PersonDTO;

public interface PersonService {

    PersonDTO add(AddPersonDTO personDTO);

    List<Person> findAll();

}
