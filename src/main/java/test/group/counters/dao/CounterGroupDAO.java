package test.group.counters.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import test.group.counters.CustomExceptions.CounterGroupNotFoundException;
import test.group.counters.CustomExceptions.CounterNotFoundException;
import test.group.counters.CustomExceptions.NotFoundException;
import test.group.counters.core.Database;
import test.group.counters.models.CounterGroupModel;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CounterGroupDAO
{
    @Autowired
    private Database database;

    public Long getNextId()
    {
        String query = "SELECT nextval('counter_group_id_seq');";

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
    public void insert(CounterGroupModel counterGroupModel) throws SQLException, Exception
    {
        String query = "INSERT INTO counter_group_model " +
                       "(id, name) " +
                       String.format("VALUES (%s, '%s');", getNextId(), counterGroupModel.getName());
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

    public void update(CounterGroupModel counterGroupModel, Long id) throws NotFoundException, SQLException {
        get(id);
        String query = "UPDATE counter_group_model " +
                       String.format("SET name = '%s' ", counterGroupModel.getName()) +
                       String.format("WHERE id = %s", id);

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

    public CounterGroupModel get(Long id) throws CounterGroupNotFoundException, SQLException {
        String query = String.format("SELECT * FROM counter_group_model WHERE id = %s;", id);
        ResultSet resultSet = database.executeGet(query);
        if (resultSet.next())
        {
            String name = resultSet.getString("name");
            resultSet.close();
            return new CounterGroupModel(id, name);
        }
        else
        {
            throw new CounterGroupNotFoundException();
        }
    }
}
