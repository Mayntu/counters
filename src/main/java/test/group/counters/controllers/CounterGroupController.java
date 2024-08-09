package test.group.counters.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import test.group.counters.dto.CountersGroupCountersCountDTO;
import test.group.counters.entities.CounterGroupModel;
import test.group.counters.services.CounterGroupService;

import java.util.List;

@RestController
@RequestMapping("/counterGroup")
public class CounterGroupController {
    private final CounterGroupService counterGroupService;

    public CounterGroupController(CounterGroupService counterGroupService) {
        this.counterGroupService = counterGroupService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('get_counter_group')")
    public CounterGroupModel apiGetCounterGroupModel(@PathVariable Long id) {
        return counterGroupService.get(id);
    }

    @GetMapping("/count")
    @PreAuthorize("hasAnyAuthority('get_counter_group')")
    public List<CountersGroupCountersCountDTO> apiGetCountersGroupCountersCount() {
        return counterGroupService.getCounters();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('post_counter_group')")
    public ResponseEntity<Void> apiPostCounterGroupModel(@Valid @RequestBody CounterGroupModel counterGroupModel) {
        counterGroupService.insert(counterGroupModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
