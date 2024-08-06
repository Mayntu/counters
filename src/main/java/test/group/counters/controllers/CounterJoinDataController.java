package test.group.counters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import test.group.counters.dao.CounterJoinDataDAO;
import test.group.counters.dto.CounterJoinDataModel;
import test.group.counters.services.ExcelEditorService;

import java.sql.SQLException;
import java.util.List;

import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/countersData")
public class CounterJoinDataController
{
    @Autowired
    private CounterJoinDataDAO counterJoinDataDAO;

    private ExcelEditorService excelEditorService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin:get', 'operator:get')")
    public List<CounterJoinDataModel> apiGetInfo() {
        return counterJoinDataDAO.info();
    }

    @GetMapping("/download")
    @PreAuthorize("hasAnyAuthority('admin:get', 'operator:get')")
    public ResponseEntity<Resource> downloadFile() throws IOException, SQLException {
        List<CounterJoinDataModel> counterJoinDataModelList = counterJoinDataDAO.info();

        excelEditorService = new ExcelEditorService("report.xls");
        excelEditorService.writeToExcel(counterJoinDataModelList);

        File file = new File("report.xls");

        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        FileSystemResource resource = new FileSystemResource(file);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .body(resource);
    }
}