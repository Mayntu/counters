package test.group.counters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.group.counters.dao.CounterJoinDataDAO;
import test.group.counters.models.CounterJoinDataModel;

import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/countersData")
public class CounterJoinDataController
{
    @Autowired
    private CounterJoinDataDAO counterJoinDataDAO;

    @GetMapping
    public ResponseEntity apiGetInfo()
    {
        try
        {
            List<CounterJoinDataModel> counterJoinDataModelList = counterJoinDataDAO.info();
            return ResponseEntity.ok(counterJoinDataModelList);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("not correct data");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("the server error");
        }
    }
}