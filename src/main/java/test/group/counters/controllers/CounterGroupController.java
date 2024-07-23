package test.group.counters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.group.counters.CustomExceptions.CounterNotFoundException;
import test.group.counters.CustomExceptions.NotFoundException;
import test.group.counters.dao.CounterGroupDAO;
import test.group.counters.models.CounterGroupModel;
import test.group.counters.services.CounterGroupService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/counterGroup")
public class CounterGroupController {
    @Autowired
    private CounterGroupService counterGroupService;

    @GetMapping
    public ResponseEntity apiGetCounterGroupModel(@RequestParam Long id)
    {
        try
        {
            CounterGroupModel counterGroupModel = counterGroupService.get(id);
            return ResponseEntity.ok(counterGroupModel);
        }
        catch (NotFoundException e)
        {
            return ResponseEntity.badRequest().body("Такой не существует");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("not correct data");
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> apiPostCounterGroupModel(@RequestBody CounterGroupModel counterGroupModel)
    {
        Map<String, String> response = new HashMap<>();
        try
        {
            counterGroupService.insert(counterGroupModel);
            response.put("result", "counter group added");
            return ResponseEntity.ok(response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.put("result", "not correct data");
        }
        return ResponseEntity.badRequest().body(response);
    }
}