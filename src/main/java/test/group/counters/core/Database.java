package test.group.counters.core;

import java.sql.*;

public class Database
{
    private final String JDBC_URL = "jdbc:postgresql://localhost:5432/meters";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "admin";

    private Connection connection;

    public Database ()
    {
        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeCommit(String query) throws SQLException
    {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
    }

    public ResultSet executeGet(String query) throws SQLException
    {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }
}
