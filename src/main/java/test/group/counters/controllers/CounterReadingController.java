package test.group.counters.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import test.group.counters.entities.CounterReadingModel;
import test.group.counters.services.CounterReadingService;

import java.util.List;

@RestController
@RequestMapping("/counterReading")
public class CounterReadingController
{
    private final CounterReadingService counterReadingService;

    public CounterReadingController(CounterReadingService counterReadingService) {
        this.counterReadingService = counterReadingService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('get_reading')")
    public Page<CounterReadingModel> apiGetAllCounterReadings(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") @Min(0) @Max(100) Integer limit
    ) {
        return counterReadingService.getAll(offset, limit);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('get_reading')")
    public CounterReadingModel apiGetCounterReading(@PathVariable Long id) {
        return counterReadingService.get(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('post_reading')")
    public ResponseEntity<Void> apiInsertCounterReading(@Valid @RequestBody CounterReadingModel counterReadingModel) {
        counterReadingService.insert(counterReadingModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('post_reading')")
    public List<CounterReadingModel> handleFileUpload(@RequestParam("file") MultipartFile file) {
        return counterReadingService.uploadFile(file);
    }
}
