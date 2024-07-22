package test.group.counters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.group.counters.CustomExceptions.CounterNotFoundException;
import test.group.counters.CustomExceptions.NotFoundException;
import test.group.counters.dao.CounterGroupDAO;
import test.group.counters.models.CounterGroupModel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/counterGroup")
public class CounterGroupController {
    @Autowired
    private CounterGroupDAO counterGroupDAO;

    @GetMapping
    public ResponseEntity apiGetCounterGroupModel(@RequestParam Long id)
    {
        try
        {
            CounterGroupModel counterGroupModel = counterGroupDAO.get(id);
            return ResponseEntity.ok(counterGroupModel);
        }
        catch (NotFoundException e)
        {
            return ResponseEntity.badRequest().body("Такой не существует");
        }
        catch (SQLException e)
        {
            return ResponseEntity.badRequest().body("sql server error");
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> apiPostCounterGroupModel(@RequestBody CounterGroupModel counterGroupModel)
    {
        Map<String, String> response = new HashMap<>();
        try
        {
            counterGroupDAO.insert(counterGroupModel);
            response.put("result", "counter group added");
            return ResponseEntity.ok(response);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            response.put("result", "not correct data");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.put("result", "sql server error");
        }
        return ResponseEntity.badRequest().body(response);
    }
}