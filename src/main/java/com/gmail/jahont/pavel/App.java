package com.gmail.jahont.pavel;

import java.lang.invoke.MethodHandles;
import java.util.List;

import com.gmail.jahont.pavel.repository.model.Person;
import com.gmail.jahont.pavel.service.PersonService;
import com.gmail.jahont.pavel.service.impl.PersonServiceImpl;
import com.gmail.jahont.pavel.service.model.AddPersonDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static void main( String[] args )
    {
        PersonService personService = new PersonServiceImpl();

        AddPersonDTO person = new AddPersonDTO();
        person.setAge(10);
        person.setName("test");
        person.setActive(true);
        personService.add(person);

        List<Person> people = personService.findAll();
        people.forEach(logger::info);
    }
}