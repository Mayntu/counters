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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import test.group.counters.controllers.CounterController;
import test.group.counters.dto.CreateCounterRequest;
import test.group.counters.models.CounterModel;
import test.group.counters.services.CounterService;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
public class CounterControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CounterService counterService;

    @InjectMocks
    private CounterController counterController;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(counterController).build();
    }

    @Test
    @WithMockUser(authorities = {"admin:get", "operator:get"})
    public void testApiGetCounter_Success() throws Exception {
        Long id = 1L;
        CounterModel counterModel = new CounterModel();
        counterModel.setId(id);
        counterModel.setName("Counter1");
        counterModel.setGroupName("Group1");
        counterModel.setUserModel(null);
        when(counterService.get(id)).thenReturn(counterModel);

        mockMvc.perform(get("/counter").param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"name\":\"Counter1\",\"groupName\":\"Group1\",\"userModel\":null}"));
    }

    @Test
    @WithMockUser(authorities = {"admin:create"})
    public void testApiInsertCounter_Success() throws Exception {
        CreateCounterRequest createCounterRequest = new CreateCounterRequest();
        createCounterRequest.setName("testCounterName");
        createCounterRequest.setGroupName("testGroupName");

        Map<String, String> userData = new HashMap<>();
        userData.put("username", "COUNTER_testCounterName");
        userData.put("password", "12345");

        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("result", "added successfully");
        expectedResponse.put("username", "COUNTER_testCounterName");
        expectedResponse.put("password", "12345");

        when(counterService.insert(any(CreateCounterRequest.class))).thenReturn(userData);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(createCounterRequest);
        String responseJson = objectMapper.writeValueAsString(expectedResponse);

        mockMvc.perform(post("/counter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJson));
    }
}
