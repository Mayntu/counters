package test.group.counters.controllers;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.group.counters.dao.CounterJoinDataDAO;
import test.group.counters.dto.CounterJoinDataDTO;

import java.util.List;


@RestController
@RequestMapping("/countersData")
public class CounterJoinDataController
{
    private final CounterJoinDataDAO counterJoinDataDAO;

    public CounterJoinDataController(CounterJoinDataDAO counterJoinDataDAO) {
        this.counterJoinDataDAO = counterJoinDataDAO;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('get_reading')")
    public List<CounterJoinDataDTO> apiGetInfo() {
        return counterJoinDataDAO.info();
    }

    @GetMapping("/download")
    @PreAuthorize("hasAnyAuthority('get_reading')")
    public ResponseEntity<Resource> downloadFile() {
        FileSystemResource resource = counterJoinDataDAO.getExcelReport();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}