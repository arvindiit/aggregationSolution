package com.microservices.eventdriven.aggregation.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest(properties = "io.reflectoring.scheduling.enabled=false")
@AutoConfigureMockMvc
public class AggregationUseCaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testEnd2EndCase() throws Exception {
        long start = System.currentTimeMillis();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("shipments", "109347265,09347263");
        map.add("track", "109347263,109347266");
        map.add("pricing", "NL,CN");
        mockMvc.perform(get("/aggregation")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParams(map))
                .andExpect(status().isOk());
        long timeTaken = System.currentTimeMillis() - start;
        assert timeTaken > 5000 && timeTaken < 10000;
    }

}
