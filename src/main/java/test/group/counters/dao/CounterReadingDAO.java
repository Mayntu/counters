package test.group.counters.dao;

import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerErrorException;
import test.group.counters.CustomExceptions.CounterReadingNotFoundException;
import test.group.counters.CustomExceptions.InvalidCounterReadingException;
import test.group.counters.core.Database;
import test.group.counters.entities.CounterReadingModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CounterReadingDAO {
    private final Database database;

    public CounterReadingDAO(Database database) {
        this.database = database;
    }

    public Long getNextId()
    {
        Long nextId = null;

        String query = "SELECT nextval('counter_reading_id_seq')";

        try (ResultSet resultSet = database.executeGet(query)) {
            if (resultSet.next()) {
                nextId = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new InvalidCounterReadingException();
        } catch (Exception e) {
            throw new ServerErrorException("internal server error", e);
        }
        return nextId;
    }

    public void insert(CounterReadingModel counterReadingModel)
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
        try {
            database.executeCommit(query);
        } catch (Exception e) {
            throw new ServerErrorException("internal server error", e);
        }
    }

    public void update(CounterReadingModel counterReadingModel, Long id)
    {
        String query = "UPDATE counter_reading_model " +
                       String.format("SET current_reading = %s, counter_id = %s, date = '%s', group_id = %s",
                               counterReadingModel.getCurrentReading(),
                               counterReadingModel.getCounterId(),
                               counterReadingModel.getDate(),
                               counterReadingModel.getGroupId()
                       ) +
                       String.format("WHERE id = %s;", id);

        try {
            database.executeCommit(query);
        } catch (SQLException e) {
            throw new InvalidCounterReadingException();
        } catch (Exception e) {
            throw new ServerErrorException("internal server error", e);
        }
    }

    public CounterReadingModel get(Long id)
    {
        String query = String.format("SELECT * FROM counter_reading_model WHERE id = %s", id);

        try (ResultSet resultSet = database.executeGet(query)) {
            if (resultSet.next())
            {
                float currentReading = resultSet.getFloat("current_reading");
                Long counterId = resultSet.getLong("counter_id");
                Long groupId = resultSet.getLong("group_id");
                String date = resultSet.getString("date");

                return new CounterReadingModel(id, counterId, groupId, date, currentReading);
            }
            throw new CounterReadingNotFoundException();
        } catch (CounterReadingNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerErrorException("internal server error", e);
        }
    }

    public List<CounterReadingModel> getAll()
    {
        String query = "SELECT * FROM counter_reading_model;";

        try (ResultSet resultSet = database.executeGet(query)) {
            List<CounterReadingModel> counterReadingModels = new ArrayList<>();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                float currentReading = resultSet.getFloat("current_reading");
                Long counterId = resultSet.getLong("counter_id");
                Long groupId = resultSet.getLong("group_id");
                String date = resultSet.getString("date");

                CounterReadingModel counterReadingModel = new CounterReadingModel(id, counterId, groupId, date, currentReading);

                counterReadingModels.add(counterReadingModel);
            }
            return counterReadingModels;
        } catch (Exception e) {
            throw new ServerErrorException("internal server error", e);
        }
    }
}
