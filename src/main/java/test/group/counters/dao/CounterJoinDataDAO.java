package test.group.counters.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerErrorException;
import test.group.counters.CustomExceptions.InvalidCounterJoinDataException;
import test.group.counters.core.Database;
import test.group.counters.models.CounterData;
import test.group.counters.dto.CounterJoinDataModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CounterJoinDataDAO  {
    @Autowired
    private Database database;

    public List<CounterJoinDataModel> info()
    {
        String query = "SELECT MIN(crm.current_reading), MAX(crm.current_reading), AVG(crm.current_reading), cem.group_name, cem.name FROM counter_reading_model AS crm JOIN counter_model cem ON crm.counter_id = cem.id GROUP BY cem.name, cem.group_name;";
        List<CounterJoinDataModel> counterJoinDataModelList = new ArrayList<>();
        try (ResultSet resultSet = database.executeGet(query))
        {
            while (resultSet.next())
            {
                String groupName = resultSet.getString("group_name");
                String name = resultSet.getString("name");
                Float min = resultSet.getFloat("min");
                Float max = resultSet.getFloat("max");
                Float avg = resultSet.getFloat("avg");

                CounterData counterData = new CounterData(name, min, max, avg);
                CounterJoinDataModel counterJoinDataModel = new CounterJoinDataModel(groupName, counterData, avg);

                counterJoinDataModelList.add(counterJoinDataModel);
            }
        }
        catch (SQLException exception)
        {
            throw new InvalidCounterJoinDataException();
        }
        catch (Exception exception)
        {
            throw new ServerErrorException("server error occurred", exception);
        }

        return counterJoinDataModelList;
    }
}