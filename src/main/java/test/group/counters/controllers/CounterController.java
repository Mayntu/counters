package test.group.counters.controllers;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import test.group.counters.dto.CreateCounterRequest;
import test.group.counters.dto.InsertedCounterDTO;
import test.group.counters.entities.CounterModel;
import test.group.counters.services.CounterService;


@RestController
@RequestMapping("/counter")
public class CounterController
{
    private final CounterService counterService;

    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('get_counter')")
    public CounterModel apiGetCounter(@PathVariable Long id)
    {
        return counterService.get(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('post_counter')")
    public InsertedCounterDTO apiInsertCounter(@Valid @RequestBody CreateCounterRequest createCounterRequest)
    {
        return counterService.insert(createCounterRequest);
    }
}
