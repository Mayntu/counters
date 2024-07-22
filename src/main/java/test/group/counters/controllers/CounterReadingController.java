package test.group.counters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.group.counters.CustomExceptions.NotFoundException;
import test.group.counters.dao.CounterDAO;
import test.group.counters.dao.CounterReadingDAO;
import test.group.counters.models.CounterModel;
import test.group.counters.models.CounterReadingModel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/counterReading")
public class CounterReadingController
{
    @Autowired
    private CounterReadingDAO counterReadingDAO;

    @GetMapping("/all")
    public ResponseEntity apiGetAllCounterReadings()
    {
        try
        {
            return ResponseEntity.ok(counterReadingDAO.getAll());
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body("not working server");
        }
    }
    @GetMapping
    public ResponseEntity apiGetCounterReading(@RequestParam Long id)
    {
        try
        {
            return ResponseEntity.ok(counterReadingDAO.get(id));
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
    public ResponseEntity<Map<String, String>> apiInsertCounterReading(@RequestBody CounterReadingModel counterReadingModel)
    {
        System.out.println("request");
        Map<String, String> response = new HashMap<>();
        try
        {
            counterReadingDAO.insert(counterReadingModel);
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
