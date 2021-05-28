package com.tourguide.rewards.service;

import com.tourguide.rewards.service.impl.RewardsServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import rewardCentral.RewardCentral;

import java.util.UUID;

@SpringBootTest
public class RewardsServiceTest {

    @Autowired
    private RewardsServiceImpl rewardsService;

    @MockBean
    private RewardCentral rewardCentral;

    @Test
    public void getAttractionRewardPointsTest() {
        Mockito.when(rewardCentral.getAttractionRewardPoints(Mockito.any(UUID.class), Mockito.any(UUID.class))).thenReturn(200);
        Assertions.assertThat(rewardsService.getAttractionRewardPoints(UUID.randomUUID(), UUID.randomUUID())).isEqualTo(200);

        // Null param

        Assertions.assertThat(rewardsService.getAttractionRewardPoints(null, null)).isEqualTo(-1);
    }

}
