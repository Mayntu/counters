package test.group.counters;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import test.group.counters.controllers.CounterReadingController;
import test.group.counters.models.CounterReadingModel;
import test.group.counters.services.CounterReadingService;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
public class CounterReadingControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CounterReadingService counterReadingService;

    @InjectMocks
    private CounterReadingController counterReadingController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(counterReadingController).build();
    }

    @Test
    @WithMockUser(authorities = {"admin:get", "operator:get"})
    public void testApiGetCounterReading_Success() throws Exception {
        Long id = 1L;
        CounterReadingModel counterReadingModel = new CounterReadingModel(id, 2L, "25.07.2024", 100.0f);

        when(counterReadingService.get(id)).thenReturn(counterReadingModel);

        mockMvc.perform(get("/counterReading").param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"counterId\":1,\"groupId\":2,\"date\":\"25.07.2024\",\"currentReading\":100.0}"));
    }

    @Test
    @WithMockUser(authorities = {"admin:create", "counter:post_reading"})
    public void testApiInsertCounterReading_Success() throws Exception {
        CounterReadingModel counterReadingModel = new CounterReadingModel(1L, 2L, "25.07.2024", 100.0f);

        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("result", "added successfully");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(counterReadingModel);
        String responseJson = objectMapper.writeValueAsString(expectedResponse);

        mockMvc.perform(post("/counterReading")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJson));
    }

    @Test
    @WithMockUser(authorities = {"admin:create", "counter:post_reading"})
    public void testHandleFileUpload_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "forTestReading.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new FileInputStream("forTestReading.xlsx"));

        doNothing().when(counterReadingService).insert(any(CounterReadingModel.class));

        mockMvc.perform(multipart("/counterReading/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
