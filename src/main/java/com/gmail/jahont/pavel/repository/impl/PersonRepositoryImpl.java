package com.gmail.jahont.pavel.repository.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gmail.jahont.pavel.repository.PersonRepository;
import com.gmail.jahont.pavel.repository.model.Person;

public class PersonRepositoryImpl extends GeneralRepositoryImpl<Person> implements PersonRepository {

    private static PersonRepository instance;

    private PersonRepositoryImpl() {
    }

    public static PersonRepository getInstance() {
        if (instance != null) {
            instance = new PersonRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Person add(Connection connection, Person person) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO person(name, age, is_active) VALUES (?,?,?)",
                        Statement.RETURN_GENERATED_KEYS
                );
        ) {
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setBoolean(3, person.getActive());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating person failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    person.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            return person;
        }
    }

    @Override
    public Person get(Connection connection, Serializable id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, name, age, is_active FROM person WHERE id=?"
                );
        ) {
            statement.setInt(1, (Integer) id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return getPerson(rs);
                }
            }
            return null;
        }
    }

    @Override
    public void update(Connection connection, Person person) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE person SET name=?, age=?, is_active=? WHERE id=?"
                );
        ) {
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setBoolean(3, person.getActive());
            statement.setInt(4, (Integer) person.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public int delete(Connection connection, Serializable id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM person WHERE id=?"
                );
        ) {
            statement.setInt(1, (Integer) id);
            return statement.executeUpdate();
        }
    }

    @Override
    public List<Person> findAll(Connection connection) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, name, age, is_active FROM person WHERE id=?"
                );
        ) {
            List<Person> people = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Person person = getPerson(rs);
                    people.add(person);
                }
                return people;
            }
        }
    }

    @Override
    public Person findByName(Connection connection, String searchName) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE name=?");
        ) {
            statement.setString(1, searchName);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return getPerson(rs);
                }
            }
            return null;
        }
    }

    private Person getPerson(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Integer age = rs.getInt("age");
        Boolean isActive = rs.getBoolean("is_active");
        return Person.newBuilder()
                .id(id)
                .name(name)
                .age(age)
                .isActive(isActive)
                .build();
    }

}
