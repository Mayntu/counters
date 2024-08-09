package test.group.counters.dao;

import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerErrorException;
import test.group.counters.CustomExceptions.CounterGroupNotFoundException;
import test.group.counters.CustomExceptions.InvalidCounterGroupException;
import test.group.counters.core.Database;
import test.group.counters.entities.CounterGroupModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CounterGroupDAO
{
    private final Database database;

    public CounterGroupDAO(Database database) {
        this.database = database;
    }

    public Long getNextId() {
        String query = "SELECT nextval('counter_group_id_seq')";

        try (PreparedStatement preparedStatement = database.getPreparedStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new InvalidCounterGroupException("no next id from sequence");
            }
        } catch (SQLException e) {
            throw new InvalidCounterGroupException("error fetching next id");
        }
    }
    public void insert(CounterGroupModel counterGroupModel) {
        String query = "INSERT INTO counter_group_model (id, name) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = database.getPreparedStatement(query)) {
            preparedStatement.setLong(1, getNextId());
            preparedStatement.setString(2, counterGroupModel.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidCounterGroupException("not valid counter data");
        }
    }

    public void update(CounterGroupModel counterGroupModel, Long id) {
        get(id);
        String query = "UPDATE counter_group_model SET name = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = database.getPreparedStatement(query)) {
            preparedStatement.setString(1, counterGroupModel.getName());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidCounterGroupException("invalid counter data");
        }
    }

    public CounterGroupModel get(Long id) {
        String query = "SELECT * FROM counter_group_model WHERE id = ?";

        try (PreparedStatement preparedStatement = database.getPreparedStatement(query)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    return new CounterGroupModel(id, name);
                } else {
                    throw new CounterGroupNotFoundException();
                }
            }
        } catch (SQLException e) {
            throw new InvalidCounterGroupException("invalid counter data : " + e.getMessage());
        }
    }
}
