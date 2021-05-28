package com.tourguide.gps.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourguide.gps.data.TestData;
import com.tourguide.gps.dto.AttractionDto;
import com.tourguide.gps.service.GpsService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GpsController.class)
@Slf4j
public class GpsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @MockBean
    private GpsService gpsService;

    @Test
    public void getNearbyAttractionsTest() throws Exception {
        Mockito.when(gpsService.getNearbyAttractions(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.any(), Mockito.any()))
                .thenAnswer(a -> {
                    Double longitude = a.getArgument(0);
                    Double latitude = a.getArgument(1);
                    Double maxDistance = a.getArgument(2);
                    Integer limit = a.getArgument(3);

                    if (longitude != null && latitude != null) {
                        if (maxDistance != null) {
                            if (limit != null && limit == 2) {
                                return TestData.getAttractionDtoList(1);
                            }
                            return TestData.getAttractionDtoList(3);
                        }
                        if (limit != null) {
                            return TestData.getAttractionDtoList(2);
                        }
                        return TestData.getAttractionDtoList();
                    }
                    return Collections.emptyList();
                });

        /*
         * Locations
         */

        MvcResult result = mvc.perform(get("/getNearbyAttractions")
                .param("longitude", "1").param("latitude", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<AttractionDto> resultList = jsonMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        Assertions.assertThat(resultList).isEqualTo(TestData.getAttractionDtoList());

        /*
         * Locations + maxDistance
         */
        result = mvc.perform(get("/getNearbyAttractions")
                .param("longitude", "1").param("latitude", "1")
                .param("maxDistance", "200")
                .param("limit", (String) null)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        resultList = jsonMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        Assertions.assertThat(resultList).isEqualTo(TestData.getAttractionDtoList(3));
        /*
         * Locations + limit
         */

        result = mvc.perform(get("/getNearbyAttractions")
                .param("longitude", "1").param("latitude", "2")
                .param("maxDistance", (String) null)
                .param("limit", "2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        resultList = jsonMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        Assertions.assertThat(resultList).isEqualTo(TestData.getAttractionDtoList(2));

        /*
         * Locations + maxDistance + limit
         */

        result = mvc.perform(get("/getNearbyAttractions")
                .param("longitude", "1").param("latitude", "1")
                .param("maxDistance", "100")
                .param("limit", "2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        resultList = jsonMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        Assertions.assertThat(resultList).isEqualTo(TestData.getAttractionDtoList(1));
    }

    @Test
    public void trackAUser() throws Exception {
        MvcResult result = mvc.perform(get("/trackAUser")
                .param("userId", UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
