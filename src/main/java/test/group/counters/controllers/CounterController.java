package test.group.counters.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import test.group.counters.dto.CreateCounterRequest;
import test.group.counters.dto.InsertCounterDTO;
import test.group.counters.models.CounterModel;
import test.group.counters.services.CounterService;


@RestController
@RequestMapping("/counter")
public class CounterController
{
    @Autowired
    private CounterService counterService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin:get', 'operator:get')")
    public CounterModel apiGetCounter(@PathVariable Long id)
    {
        return counterService.get(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public InsertCounterDTO apiInsertCounter(@Valid @RequestBody CreateCounterRequest createCounterRequest)
    {
        return counterService.insert(createCounterRequest);
    }
}
