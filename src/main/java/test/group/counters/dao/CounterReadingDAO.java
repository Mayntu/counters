package test.group.counters.dao;

import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerErrorException;
import test.group.counters.CustomExceptions.CounterReadingNotFoundException;
import test.group.counters.CustomExceptions.InvalidCounterReadingException;
import test.group.counters.core.Database;
import test.group.counters.entities.CounterReadingModel;

import java.sql.PreparedStatement;
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

    public Long getNextId() {
        String query = "SELECT nextval('counter_reading_id_seq')";

        try (PreparedStatement preparedStatement = database.getPreparedStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new InvalidCounterReadingException("no next id from sequence");
            }
        } catch (SQLException e) {
            throw new InvalidCounterReadingException("error fetching next id");
        }
    }

    public void insert(CounterReadingModel counterReadingModel) {
        String query = "INSERT INTO counter_reading_model (id, current_reading, counter_id, date, group_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = database.getPreparedStatement(query)) {
            preparedStatement.setLong(1, getNextId());
            preparedStatement.setFloat(2, counterReadingModel.getCurrentReading());
            preparedStatement.setLong(3, counterReadingModel.getCounterId());
            preparedStatement.setString(4, counterReadingModel.getDate());
            preparedStatement.setLong(5, counterReadingModel.getGroupId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidCounterReadingException("not valid counter reading data");
        }
    }

    public void update(CounterReadingModel counterReadingModel, Long id)
    {
        String query = "UPDATE counter_reading_model SET current_reading = ?, counter_id = ?, date = ?, group_id = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = database.getPreparedStatement(query)) {
            preparedStatement.setFloat(1, counterReadingModel.getCurrentReading());
            preparedStatement.setLong(2, counterReadingModel.getCounterId());
            preparedStatement.setString(3, counterReadingModel.getDate());
            preparedStatement.setLong(4, counterReadingModel.getGroupId());
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidCounterReadingException();
        }
    }

    public CounterReadingModel get(Long id)
    {
        String query = "SELECT * FROM counter_reading_model WHERE id = ?";

        try (PreparedStatement preparedStatement = database.getPreparedStatement(query)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    float currentReading = resultSet.getFloat("current_reading");
                    Long counterId = resultSet.getLong("counter_id");
                    Long groupId = resultSet.getLong("group_id");
                    String date = resultSet.getString("date");

                    return new CounterReadingModel(id, counterId, groupId, date, currentReading);
                } else {
                    throw new CounterReadingNotFoundException("Counter reading not found with ID: " + id);
                }
            }
        } catch (SQLException e) {
            throw new InvalidCounterReadingException("not valid id form : " + id);
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
