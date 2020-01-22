package com.gmail.jahont.pavel.repository;

import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.jahont.pavel.repository.model.Person;

public interface PersonRepository extends GeneralRepository<Person> {

    Person findByName(Connection connection, String searchName) throws SQLException;

}
