package test.group.counters.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import test.group.counters.CustomExceptions.CounterReadingNotFoundException;
import test.group.counters.CustomExceptions.NotFoundException;
import test.group.counters.core.Database;
import test.group.counters.models.CounterReadingModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequestMapping("/counterReading")
public class CounterReadingDAO {
    @Autowired
    private Database database;

    public Long getNextId()
    {
        Long nextId = null;

        String query = "SELECT nextval('counter_reading_id_seq')";

        try (ResultSet resultSet = database.executeGet(query)) {
            if (resultSet.next()) {
                nextId = resultSet.getLong(1);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            System.out.println("server error now");
        }
        return nextId;
    }

    public void insert(CounterReadingModel counterReadingModel) throws SQLException
    {
        String query = "INSERT INTO counter_reading_model " +
                       "(id, current_reading, counter_id, date, group_id) " +
                       String.format("VALUES (%s, %s, %s, '%s', %s)",
                               getNextId(),
                               counterReadingModel.getCurrentReading(),
                               counterReadingModel.getCounterId(),
                               counterReadingModel.getDate(),
                               counterReadingModel.getGroupId()
                       );
        System.out.println(query);
        database.executeCommit(query);
    }

    public void update(CounterReadingModel counterReadingModel, Long id) throws SQLException
    {
        String query = "UPDATE counter_reading_model " +
                       String.format("SET current_reading = %s, counter_id = %s, date = '%s', group_id = %s",
                               counterReadingModel.getCurrentReading(),
                               counterReadingModel.getCounterId(),
                               counterReadingModel.getDate(),
                               counterReadingModel.getGroupId()
                       ) +
                       String.format("WHERE id = %s;", id);
        System.out.println(query);
        database.executeCommit(query);
    }

    public CounterReadingModel get(Long id) throws SQLException, NotFoundException
    {
        String query = String.format("SELECT * FROM counter_reading_model WHERE id = %s", id);

        ResultSet resultSet = database.executeGet(query);

        if (resultSet.next())
        {
            Float currentReading = resultSet.getFloat("current_reading");
            Long counterId = resultSet.getLong("counter_id");
            Long groupId = resultSet.getLong("group_id");
            String date = resultSet.getString("date");

            return new CounterReadingModel(id, counterId, groupId, date, currentReading);
        }
        throw new CounterReadingNotFoundException();
    }

    public List<CounterReadingModel> getAll() throws SQLException
    {
        String query = "SELECT * FROM counter_reading_model;";

        ResultSet resultSet = database.executeGet(query);

        List<CounterReadingModel> counterReadingModels = new ArrayList<>();

        while (resultSet.next())
        {
            Long id = resultSet.getLong("id");
            Float currentReading = resultSet.getFloat("current_reading");
            Long counterId = resultSet.getLong("counter_id");
            Long groupId = resultSet.getLong("group_id");
            String date = resultSet.getString("date");

            CounterReadingModel counterReadingModel = new CounterReadingModel(id, counterId, groupId, date, currentReading);

            counterReadingModels.add(counterReadingModel);
        }
        resultSet.close();
        return counterReadingModels;
    }
}
