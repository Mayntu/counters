package test.group.counters.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import test.group.counters.CustomExceptions.CounterNotFoundException;
import test.group.counters.CustomExceptions.NotFoundException;
import test.group.counters.core.Database;
import test.group.counters.models.CounterModel;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CounterDAO
{
    @Autowired
    private Database database;

    public Long getNextId()
    {
        String query = "SELECT nextval('counter_id_seq');";

        Long nextId = null;

        try (ResultSet resultSet = database.executeGet(query))
        {
            if (resultSet.next()) {
                nextId = resultSet.getLong(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return nextId;
    }
    public void insert(CounterModel counterModel) throws SQLException, Exception
    {
        String query = "INSERT INTO counter_model " +
                "(id, name, group_name)" +
                String.format("VALUES (%s, '%s', '%s');", getNextId(), counterModel.getName(), counterModel.getGroupName());
        System.out.println(query);
        try
        {
            database.executeCommit(query);
        }
        catch (SQLException exception)
        {
            throw exception;
        }
        catch (Exception exception)
        {
            throw new RuntimeException();
        }
    }

    public void update(CounterModel counterModel, Long id) throws NotFoundException, SQLException {
        get(id);
        String query = String.format("UPDATE counter_model " +
                "SET name = '%s', group_name = '%s'" +
                "WHERE id = %s", counterModel.getName(), counterModel.getGroupName(), id);
        System.out.println(query);
        try
        {
            database.executeCommit(query);
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
            System.out.println("not working data");
        }
        catch (Exception exception)
        {
            throw new RuntimeException();
        }
    }

    public CounterModel get(Long id) throws CounterNotFoundException, SQLException {
        String query = String.format("SELECT * FROM counter_model WHERE id = %s;", id);
        ResultSet resultSet = database.executeGet(query);
        if (resultSet.next())
        {
            String name = resultSet.getString("name");
            String groupName = resultSet.getString("group_name");
            resultSet.close();
            return new CounterModel(id, name, groupName);
        }
        else
        {
            throw new CounterNotFoundException();
        }
    }
}
