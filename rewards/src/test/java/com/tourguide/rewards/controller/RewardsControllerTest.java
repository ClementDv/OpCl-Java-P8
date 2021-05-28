package com.tourguide.rewards.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourguide.rewards.service.RewardsService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RewardsController.class)
public class RewardsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @MockBean
    private RewardsService rewardsService;

    @Test
    public void getRewardPointsTest() throws Exception {

        Mockito.when(rewardsService.getAttractionRewardPoints(Mockito.any(UUID.class), Mockito.any(UUID.class))).thenReturn(200);

        MvcResult result = mvc.perform(get("/getAttractionRewardPoints")
                .param("attractionId", UUID.randomUUID().toString())
                .param("userId", UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Integer resultInt = jsonMapper.readValue(result.getResponse().getContentAsString(), Integer.class);
        Assertions.assertThat(resultInt).isEqualTo(200);
    }

}
