package test.group.counters.controllers;

import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import test.group.counters.CustomExceptions.CounterNotFoundException;
import test.group.counters.CustomExceptions.NotFoundException;
import test.group.counters.dao.CounterGroupDAO;
import test.group.counters.dto.CountersGroupCountersCountDTO;
import test.group.counters.models.CounterGroupModel;
import test.group.counters.services.CounterGroupService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/counterGroup")
public class CounterGroupController {
    @Autowired
    private CounterGroupService counterGroupService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin:get', 'operator:get')")
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

    @GetMapping("/countersCount")
    @PreAuthorize("hasAnyAuthority('admin:get', 'operator:get')")
    public ResponseEntity apiGetCountersGroupCountersCount()
    {
        try
        {
            List<CountersGroupCountersCountDTO> countersGroupCountersCount = counterGroupService.getCounters();


            return ResponseEntity.ok(countersGroupCountersCount);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();

            return ResponseEntity.badRequest().body("service not working");
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
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
