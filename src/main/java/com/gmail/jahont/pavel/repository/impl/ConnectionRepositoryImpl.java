package com.gmail.jahont.pavel.repository.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.jahont.pavel.repository.ConnectionRepository;
import com.gmail.jahont.pavel.util.PropertyUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import static com.gmail.jahont.pavel.repository.constant.ConnectionConstant.DATABASE_PASSWORD;
import static com.gmail.jahont.pavel.repository.constant.ConnectionConstant.DATABASE_URL;
import static com.gmail.jahont.pavel.repository.constant.ConnectionConstant.DATABASE_USERNAME;

public class ConnectionRepositoryImpl implements ConnectionRepository {

    private static ConnectionRepository instance;

    private ConnectionRepositoryImpl() {
    }

    public static ConnectionRepository getInstance() {
        if (instance == null) {
            instance = new ConnectionRepositoryImpl();
        }
        return instance;
    }

    private static HikariDataSource ds;

    @Override
    public Connection getConnection() throws SQLException {
        if (ds == null) {
            PropertyUtil propertyUtil = new PropertyUtil();
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(propertyUtil.getProperty(DATABASE_URL));
            config.setUsername(propertyUtil.getProperty(DATABASE_USERNAME));
            config.setPassword(propertyUtil.getProperty(DATABASE_PASSWORD));
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            ds = new HikariDataSource(config);
        }
        return ds.getConnection();
    }

}