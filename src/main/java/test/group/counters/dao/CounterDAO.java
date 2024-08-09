package test.group.counters.dao;

import org.springframework.stereotype.Repository;
import test.group.counters.CustomExceptions.CounterNotFoundException;
import test.group.counters.CustomExceptions.InvalidCounterException;
import test.group.counters.core.Database;
import test.group.counters.entities.CounterModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CounterDAO
{
    private final Database database;

    public CounterDAO(Database database) {
        this.database = database;
    }

    public Long getNextId() {
        String query = "SELECT nextval('counter_id_seq')";

        try (PreparedStatement preparedStatement = database.getPreparedStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new InvalidCounterException("no next id from sequence");
            }
        } catch (SQLException e) {
            throw new InvalidCounterException("error fetching next id");
        }
    }
    public void insert(CounterModel counterModel) {
        String query = "INSERT INTO counter_model (id, name, group_name) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = database.getPreparedStatement(query)) {
            preparedStatement.setLong(1, getNextId());
            preparedStatement.setString(2, counterModel.getName());
            preparedStatement.setString(3, counterModel.getGroupName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidCounterException("invalid counter data : " + e.getMessage());
        }
    }

    public void update(CounterModel counterModel, Long id) {
        get(id);
        String query = "UPDATE counter_model SET name = ?, group_name = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = database.getPreparedStatement(query)) {
            preparedStatement.setString(1, counterModel.getName());
            preparedStatement.setString(2, counterModel.getGroupName());
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidCounterException("invalid counter data : " + e.getMessage());
        }
    }

    public CounterModel get(Long id) {
        String query = "SELECT * FROM counter_model WHERE id = ?";

        try (PreparedStatement preparedStatement = database.getPreparedStatement(query)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String groupName = resultSet.getString("group_name");
                    return new CounterModel(id, name, groupName, null);
                } else {
                    throw new CounterNotFoundException();
                }
            }
        } catch (SQLException e) {
            throw new InvalidCounterException("invalid counter data : " + e.getMessage());
        }
    }
}
