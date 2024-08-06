package test.group.counters.controllers;

import jakarta.persistence.Tuple;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin:get', 'operator:get')")
    public CounterGroupModel apiGetCounterGroupModel(@PathVariable Long id)
    {
        return counterGroupService.get(id);
    }

    @GetMapping("/count")
    @PreAuthorize("hasAnyAuthority('admin:get', 'operator:get')")
    public List<CountersGroupCountersCountDTO> apiGetCountersGroupCountersCount()
    {
        return counterGroupService.getCounters();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<Void> apiPostCounterGroupModel(@Valid @RequestBody CounterGroupModel counterGroupModel)
    {
        counterGroupService.insert(counterGroupModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
