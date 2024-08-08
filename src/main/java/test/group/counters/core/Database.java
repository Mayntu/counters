package test.group.counters.core;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import test.group.counters.CustomExceptions.InvalidDataException;

import java.sql.*;

@Service
public class Database {
    @Value("${spring.datasource.url}")
    private String JDBC_URL;
    @Value("${spring.datasource.username}")
    private String USERNAME;
    @Value("${spring.datasource.password}")
    private String PASSWORD;

    private Connection connection;

    @PostConstruct
    public void initializeConnection () {
        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeCommit(String query) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException exception) {
            throw new InvalidDataException("not valid query");
        }
    }

    public ResultSet executeGet(String query) {
        try (Statement statement = connection.createStatement()){
            return statement.executeQuery(query);
        } catch (SQLException e) {
            throw new InvalidDataException("not valid query");
        }
    }

    @PreDestroy
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
