package com.tourguide.users.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@SpringBootTest
public class RestRewardsServiceTest {

    @Autowired
    private RestRewardService restRewardService;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void getAttractionRewardPoint() {

        // Normal

        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any()))
                .thenReturn(new ResponseEntity(200, HttpStatus.OK));

        Assertions.assertThat(restRewardService.getAttractionRewardPoint(UUID.randomUUID(), UUID.randomUUID()))
                .isEqualTo(200);

        // -1 Error Return

        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any()))
                .thenReturn(new ResponseEntity(-1, HttpStatus.OK));

        Assertions.assertThat(restRewardService.getAttractionRewardPoint(UUID.randomUUID(), UUID.randomUUID()))
                .isEqualTo(-1);


        // Null Return

        Integer rewardsValueTest = null;

        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any()))
                .thenReturn(new ResponseEntity(rewardsValueTest, HttpStatus.OK));

        Assertions.assertThat(restRewardService.getAttractionRewardPoint(UUID.randomUUID(), UUID.randomUUID()))
                .isEqualTo(-1);
    }

}
