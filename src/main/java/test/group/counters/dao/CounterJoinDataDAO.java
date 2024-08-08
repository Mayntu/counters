package test.group.counters.dao;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerErrorException;
import test.group.counters.CustomExceptions.ExcelFileNotFoundException;
import test.group.counters.CustomExceptions.InvalidCounterJoinDataException;
import test.group.counters.core.Database;
import test.group.counters.dto.CounterDataDTO;
import test.group.counters.dto.CounterJoinDataDTO;
import test.group.counters.services.ExcelEditorService;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CounterJoinDataDAO  {
    private final Database database;

    public CounterJoinDataDAO(Database database)
    {
        this.database = database;
    }

    public List<CounterJoinDataDTO> info() {
        String query = "SELECT MIN(crm.current_reading), MAX(crm.current_reading), AVG(crm.current_reading), cem.group_name, cem.name FROM counter_reading_model AS crm JOIN counter_model cem ON crm.counter_id = cem.id GROUP BY cem.name, cem.group_name;";
        List<CounterJoinDataDTO> counterJoinDataModelList = new ArrayList<>();
        try (ResultSet resultSet = database.executeGet(query)) {
            while (resultSet.next()) {
                String groupName = resultSet.getString("group_name");
                String name = resultSet.getString("name");
                Float min = resultSet.getFloat("min");
                Float max = resultSet.getFloat("max");
                Float avg = resultSet.getFloat("avg");

                CounterDataDTO counterDataDTO = new CounterDataDTO(name, min, max, avg);
                CounterJoinDataDTO counterJoinDataDTO = new CounterJoinDataDTO(groupName, counterDataDTO, avg);

                counterJoinDataModelList.add(counterJoinDataDTO);
            }
        } catch (SQLException e) {
            throw new InvalidCounterJoinDataException();
        } catch (Exception e) {
            throw new ServerErrorException("internal server error", e);
        }

        return counterJoinDataModelList;
    }

    public FileSystemResource getExcelReport() {
        List<CounterJoinDataDTO> counterJoinDataModelList = info();

        String fileName = generateReportName();
        ExcelEditorService.writeToExcel(counterJoinDataModelList, fileName);

        File file = new File(fileName);

        if (!file.exists()) {
            throw new ExcelFileNotFoundException();
        }

        return new FileSystemResource(file);
    }

    public String generateReportName() {
        UUID uuid = UUID.randomUUID();
        return String.format("report_%s.xls", uuid);
    }
}