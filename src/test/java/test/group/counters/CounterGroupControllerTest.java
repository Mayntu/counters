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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import test.group.counters.controllers.CounterGroupController;
import test.group.counters.dto.CountersGroupCountersCountDTO;
import test.group.counters.models.CounterGroupModel;
import test.group.counters.services.CounterGroupService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
public class CounterGroupControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CounterGroupService counterGroupService;

    @InjectMocks
    private CounterGroupController counterGroupController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(counterGroupController).build();
    }

    @Test
    @WithMockUser(authorities = {"admin:get", "operator:get"})
    public void testApiGetCounterGroupModel_Success() throws Exception {
        Long id = 1L;
        CounterGroupModel counterGroupModel = new CounterGroupModel();
        counterGroupModel.setId(id);
        counterGroupModel.setName("CounterGroup1");

        when(counterGroupService.get(id)).thenReturn(counterGroupModel);

        mockMvc.perform(get("/counterGroup").param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"name\":\"CounterGroup1\"}"));
    }

    @Test
    @WithMockUser(authorities = {"admin:get", "operator:get"})
    public void testApiGetCountersGroupCountersCount_Success() throws Exception {
        List<CountersGroupCountersCountDTO> countersGroupCountersCountDTOs = Collections.singletonList(new CountersGroupCountersCountDTO("Group1", 10L));

        when(counterGroupService.getCounters()).thenReturn(countersGroupCountersCountDTOs);

        mockMvc.perform(get("/counterGroup/countersCount"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"groupName\":\"Group1\",\"countersCount\":10}]"));
    }

    @Test
    @WithMockUser(authorities = {"admin:create"})
    public void testApiPostCounterGroupModel_Success() throws Exception {
        CounterGroupModel counterGroupModel = new CounterGroupModel();
        counterGroupModel.setId(1L);
        counterGroupModel.setName("NewCounterGroup");

        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("result", "counter group added");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(counterGroupModel);
        String responseJson = objectMapper.writeValueAsString(expectedResponse);

        mockMvc.perform(post("/counterGroup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJson));
    }
}
