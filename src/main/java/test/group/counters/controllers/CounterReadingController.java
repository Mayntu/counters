package test.group.counters.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import test.group.counters.CustomExceptions.NotFoundException;
import test.group.counters.models.CounterReadingModel;
import test.group.counters.services.CounterReadingService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/counterReading")
public class CounterReadingController
{
    @Autowired
    private CounterReadingService counterReadingService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('admin:get', 'operator:get')")
    public List<CounterReadingModel> apiGetAllCounterReadings()
    {
        return counterReadingService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin:get', 'operator:get')")
    public CounterReadingModel apiGetCounterReading(@PathVariable Long id)
    {
        return counterReadingService.get(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin:create', 'counter:post_reading')")
    public ResponseEntity<Void> apiInsertCounterReading(@Valid @RequestBody CounterReadingModel counterReadingModel)
    {
        counterReadingService.insert(counterReadingModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('admin:create', 'counter:post_reading')")
    public ResponseEntity<List<CounterReadingModel>> handleFileUpload(@RequestParam("file") MultipartFile file) {
        List<CounterReadingModel> excelData = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            int index = 0;

            for (Row row : sheet) {
                if (index == 0)
                {
                    index = 1;
                    continue;
                }
                CounterReadingModel counterReadingModel;
                counterReadingModel = new CounterReadingModel(
                        (long) (row.getCell(0).getNumericCellValue()),
                        (long) (row.getCell(1).getNumericCellValue()),
                        row.getCell(2).toString(),
                        Float.parseFloat(row.getCell(3).toString())
                );
                counterReadingService.insert(counterReadingModel);
                excelData.add(counterReadingModel);
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(excelData);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
