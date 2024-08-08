package test.group.counters.services;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;
import test.group.counters.CustomExceptions.CounterReadingNotFoundException;
import test.group.counters.entities.CounterReadingModel;
import test.group.counters.repositories.CounterReadingRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class CounterReadingService
{
    private final CounterReadingRepository counterReadingRepository;

    public CounterReadingService(CounterReadingRepository counterReadingRepository) {
        this.counterReadingRepository = counterReadingRepository;
    }

    public Page<CounterReadingModel> getAll(Integer offset, Integer limit) {
        try {
            return counterReadingRepository.findAll(PageRequest.of(offset, limit));
        } catch (Exception e) {
            throw new ServerErrorException("internal server error", e);
        }
    }

    public CounterReadingModel get(Long id) {
        return counterReadingRepository.findById(id).orElseThrow(CounterReadingNotFoundException::new);
    }

    public void insert(CounterReadingModel counterReadingModel) {
        counterReadingRepository.save(counterReadingModel);
    }

    public List<CounterReadingModel> uploadFile(MultipartFile fileToUpload) {
        List<CounterReadingModel> excelData = new ArrayList<>();

        try (InputStream inputStream = fileToUpload.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            int index = 0;

            for (Row row : sheet) {
                if (index == 0) {
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
                insert(counterReadingModel);
                excelData.add(counterReadingModel);
            }

        } catch (IOException e) {
            throw new ServerErrorException("internal server error", e);
        }

        return excelData;
    }
}
