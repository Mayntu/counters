package test.group.counters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.group.counters.CustomExceptions.NotFoundException;
import test.group.counters.dao.CounterDAO;
import test.group.counters.models.CounterModel;
import test.group.counters.services.CounterService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/counter")
public class CounterController
{
    @Autowired
    private CounterService counterService;

    @GetMapping
    public ResponseEntity apiGetCounter(@RequestParam Long id)
    {
        try
        {
            CounterModel counterModel = counterService.get(id);
            return ResponseEntity.ok(counterModel);
        }
        catch (NotFoundException e)
        {
            return ResponseEntity.badRequest().body("Такой не существует");
        }
        catch (Exception e)
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
            counterService.insert(counterModel);
            response.put("result", "added successfully");
            return ResponseEntity.ok(response);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            response.put("result", "not correct data");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
