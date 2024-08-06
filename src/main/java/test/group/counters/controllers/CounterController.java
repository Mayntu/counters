package test.group.counters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import test.group.counters.CustomExceptions.NotFoundException;
import test.group.counters.dao.CounterDAO;
import test.group.counters.dto.CreateCounterRequest;
import test.group.counters.models.CounterModel;
import test.group.counters.models.UserModel;
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

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin:get', 'operator:get')")
    public ResponseEntity<CounterModel> apiGetCounter(@PathVariable Long id)
    {
        CounterModel counterModel = counterService.get(id);
        return ResponseEntity.ok(counterModel);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<Map<String, String>> apiInsertCounter(@RequestBody CreateCounterRequest createCounterRequest)
    {
        System.out.println("request");
        Map<String, String> response = new HashMap<>();
        try {
            Map<String, String> userData = counterService.insert(createCounterRequest);
            response.put("result", "added successfully");
            response.put("username", userData.get("username"));
            response.put("password", userData.get("password"));
            return new ResponseEntity(response, HttpStatus.CREATED);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            response.put("result", "not correct data");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
