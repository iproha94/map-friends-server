package com.wordpress.ilyaps.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by ilyaps on 13.04.16.
 */
public class DBService {
    static final Logger LOGGER = LogManager.getLogger(DBService.class);

    private final String nameDriver;
    private final String jdbcUrl;
    private final String userName;
    private final String password;

    public DBService(Properties propertiesDB) {
        this.nameDriver = propertiesDB.getProperty("name_driver");
        this.jdbcUrl = propertiesDB.getProperty("jdbc_url");
        this.userName = propertiesDB.getProperty("user_name");
        this.password = propertiesDB.getProperty("user_password");

        try {
            Driver driver = (Driver) Class.forName(nameDriver).newInstance();
            DriverManager.registerDriver(driver);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ignored) {
            LOGGER.error(ignored);
            throw new RuntimeException();
        }

        try (Connection con = openConnection()) {
            loggerInfoOfConnection(con);
        } catch (SQLException ignored) {
            LOGGER.warn(ignored);
        }
    }

    public Connection openConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(jdbcUrl, userName, password);
        } catch (SQLException ignored) {
            LOGGER.error("open connection");
            LOGGER.error(ignored);
        }

        if (con == null) {
            LOGGER.error("connection == null");
            throw new NullPointerException();
        }
        return con;
    }

    public void loggerInfoOfConnection(Connection connection) {
        if (connection == null) {
            LOGGER.info("connection == null");
            return;
        }

        try {
            LOGGER.info("Autocommit: " + connection.getAutoCommit());
            LOGGER.info("db name: " + connection.getMetaData().getDatabaseProductName());
            LOGGER.info("db version: " + connection.getMetaData().getDatabaseProductVersion());
            LOGGER.info("Driver name: " + connection.getMetaData().getDriverName());
            LOGGER.info("Driver version: " + connection.getMetaData().getDriverVersion());
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }
}
