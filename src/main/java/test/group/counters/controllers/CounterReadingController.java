package test.group.counters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity apiGetAllCounterReadings()
    {
        try
        {
            return ResponseEntity.ok(counterReadingService.getAll());
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body("not working server");
        }
    }
    @GetMapping
    public ResponseEntity apiGetCounterReading(@RequestParam Long id)
    {
        try
        {
            return ResponseEntity.ok(counterReadingService.get(id));
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
    public ResponseEntity<Map<String, String>> apiInsertCounterReading(@RequestBody CounterReadingModel counterReadingModel)
    {
        System.out.println("request");
        Map<String, String> response = new HashMap<>();
        try
        {
            counterReadingService.insert(counterReadingModel);
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

    @PostMapping("/upload")
    public ResponseEntity<List<String>> handleFileUpload(@RequestParam("file") MultipartFile file) {
        List<String> excelData = new ArrayList<>();

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
//                CounterReadingModel counterReadingModel;
                counterReadingService.insert(
                        new CounterReadingModel(
                                (long) (row.getCell(0).getNumericCellValue()),
                                (long) (row.getCell(1).getNumericCellValue()),
                                row.getCell(2).toString(),
                                Float.parseFloat(row.getCell(3).toString())
                        )
                );
                excelData.add("counterReadingModel");
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(excelData);
    }
}
