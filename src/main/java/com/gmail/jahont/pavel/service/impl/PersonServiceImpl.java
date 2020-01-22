package com.gmail.jahont.pavel.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.gmail.jahont.pavel.repository.ConnectionRepository;
import com.gmail.jahont.pavel.repository.PersonRepository;
import com.gmail.jahont.pavel.repository.impl.ConnectionRepositoryImpl;
import com.gmail.jahont.pavel.repository.impl.PersonRepositoryImpl;
import com.gmail.jahont.pavel.repository.model.Person;
import com.gmail.jahont.pavel.service.PersonService;
import com.gmail.jahont.pavel.service.model.AddPersonDTO;
import com.gmail.jahont.pavel.service.model.PersonDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersonServiceImpl implements PersonService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final String ADULT_VALUE = "adult";
    private static final int ADULT_AGE_VALUE = 21;
    private ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    private PersonRepository personRepository = PersonRepositoryImpl.getInstance();

    @Override
    public PersonDTO add(AddPersonDTO personDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Person person = convertDTOToPerson(personDTO);
                person = personRepository.add(connection, person);
                PersonDTO personToDTO = convertPersonToDTO(person);
                connection.commit();
                return personToDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private PersonDTO convertPersonToDTO(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(person.getName());
        applyCondition(person, personDTO);
        return personDTO;
    }

    private void applyCondition(Person person, PersonDTO personDTO) {
        if (person.getActive()) {
            if (person.getAge() >= ADULT_AGE_VALUE) {
                personDTO.setType(ADULT_VALUE);
            } else {
                personDTO.setType("no " + ADULT_VALUE);
            }
        }
    }

    private Person convertDTOToPerson(AddPersonDTO personDTO) {
        return Person.newBuilder()
                .name(personDTO.getName())
                .age(personDTO.getAge())
                .isActive(personDTO.isActive())
                .build();
    }

    @Override
    public List<Person> findAll() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Person> people = personRepository.findAll(connection);
                connection.commit();
                return people;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

}
