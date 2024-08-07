package test.group.counters.dao;

import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerErrorException;
import test.group.counters.CustomExceptions.CounterGroupNotFoundException;
import test.group.counters.CustomExceptions.InvalidCounterGroupException;
import test.group.counters.core.Database;
import test.group.counters.entities.CounterGroupModel;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CounterGroupDAO
{
    private final Database database;

    public CounterGroupDAO(Database database) {
        this.database = database;
    }

    public Long getNextId()
    {
        String query = "SELECT nextval('counter_group_id_seq');";

        Long nextId = null;

        try (ResultSet resultSet = database.executeGet(query)) {
            if (resultSet.next()) {
                nextId = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new InvalidCounterGroupException();
        } catch (Exception e) {
            throw new ServerErrorException("internal server error", e);
        }

        return nextId;
    }
    public void insert(CounterGroupModel counterGroupModel)
    {
        String query = "INSERT INTO counter_group_model " +
                       "(id, name) " +
                       String.format("VALUES (%s, '%s');", getNextId(), counterGroupModel.getName());

        try {
            database.executeCommit(query);
        } catch (SQLException exception) {
            throw new InvalidCounterGroupException();
        }
    }

    public void update(CounterGroupModel counterGroupModel, Long id) {
        get(id);
        String query = "UPDATE counter_group_model " +
                       String.format("SET name = '%s' ", counterGroupModel.getName()) +
                       String.format("WHERE id = %s", id);
        try {
            database.executeCommit(query);
        } catch (SQLException e) {
            throw new InvalidCounterGroupException();
        } catch (Exception e) {
            throw new ServerErrorException("internal server error", e);
        }
    }

    public CounterGroupModel get(Long id) {
        String query = String.format("SELECT * FROM counter_group_model WHERE id = %s;", id);

        try (ResultSet resultSet = database.executeGet(query)) {
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                return new CounterGroupModel(id, name);
            }
            else {
                throw new CounterGroupNotFoundException();
            }
        } catch (Exception e) {
            throw new ServerErrorException("internal server error", e);
        }
    }
}
