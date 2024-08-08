package test.group.counters.dao;

import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerErrorException;
import test.group.counters.CustomExceptions.CounterNotFoundException;
import test.group.counters.CustomExceptions.InvalidCounterException;
import test.group.counters.core.Database;
import test.group.counters.entities.CounterModel;

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
        String query = "SELECT nextval('counter_id_seq');";

        Long nextId = null;

        try (ResultSet resultSet = database.executeGet(query)) {
            if (resultSet.next()) {
                nextId = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new InvalidCounterException();
        } catch (Exception e) {
            throw new ServerErrorException("internal server error", e);
        }

        return nextId;
    }
    public void insert(CounterModel counterModel) {
        String query = "INSERT INTO counter_model " +
                "(id, name, group_name)" +
                String.format("VALUES (%s, '%s', '%s');", getNextId(), counterModel.getName(), counterModel.getGroupName());

        try {
            database.executeCommit(query);
        } catch (Exception exception) {
            throw new InvalidCounterException();
        }
    }

    public void update(CounterModel counterModel, Long id) {
        get(id);
        String query = String.format("UPDATE counter_model " +
                "SET name = '%s', group_name = '%s'" +
                "WHERE id = %s", counterModel.getName(), counterModel.getGroupName(), id);
        try {
            database.executeCommit(query);
        } catch (Exception e) {
            throw new InvalidCounterException(e.getMessage());
        }
    }

    public CounterModel get(Long id) {
        String query = String.format("SELECT * FROM counter_model WHERE id = %s;", id);

        try (ResultSet resultSet = database.executeGet(query)) {
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String groupName = resultSet.getString("group_name");
                return new CounterModel(id, name, groupName, null);
            }
            else {
                throw new CounterNotFoundException();
            }
        } catch (CounterNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerErrorException("internal server error", e);
        }
    }
}
