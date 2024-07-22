package test.group.counters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.group.counters.CustomExceptions.NotFoundException;
import test.group.counters.dao.CounterDAO;
import test.group.counters.models.CounterModel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/counter")
public class CounterController
{
    @Autowired
    private CounterDAO counterDAO;

    @GetMapping
    public ResponseEntity apiGetCounter(@RequestParam Long id)
    {
        try
        {
            CounterModel counterModel = counterDAO.get(id);
            return ResponseEntity.ok(counterModel);
        }
        catch (NotFoundException e)
        {
            return ResponseEntity.badRequest().body("Такой не существует");
        }
        catch (SQLException e)
        {
            return ResponseEntity.badRequest().body("server error");
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> apiInsertCounter(@RequestBody CounterModel counterModel)
    {
        System.out.println("request");
        Map<String, String> response = new HashMap<>();
        try
        {
            counterDAO.insert(counterModel);
            response.put("result", "added successfully");
            return ResponseEntity.ok(response);
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
            response.put("result", "not correct data");
            return ResponseEntity.badRequest().body(response);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            response.put("result", "server error occurred");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
