package com.gmail.jahont.pavel.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GeneralRepository<T> {

    T add(Connection connection, T t) throws SQLException;

    T get(Connection connection, Serializable id) throws SQLException;

    void update(Connection connection, T t) throws SQLException;

    int delete(Connection connection, Serializable id) throws SQLException;

    List<T> findAll(Connection connection) throws SQLException;

}


