package com.tourguide.users.service;

import com.tourguide.users.data.TestData;
import com.tourguide.users.entity.User;
import com.tourguide.users.entity.UserReward;
import com.tourguide.users.service.impl.TripPricerServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TripPricerServiceTest {

    @Autowired
    private TripPricerServiceImpl tripPricerService;

    @MockBean
    private TripPricer tripPricer;

    @Test
    public void getTripDealsTest() {
        User user = TestData.generateAUser("Test");
        user.setUserRewards(new ArrayList<>(Arrays.asList(
                UserReward.builder().rewardPoints(200).attraction(null).visitedLocation(null).build(),
                UserReward.builder().rewardPoints(300).attraction(null).visitedLocation(null).build(),
                UserReward.builder().rewardPoints(180).attraction(null).visitedLocation(null).build())));

        List<Provider> providerList = TestData.getProviderList();

        Mockito.when(tripPricer.getPrice(
                Mockito.any(),
                Mockito.eq(user.getUserId()),
                Mockito.eq(user.getUserPreferences().getNumberOfAdults()),
                Mockito.eq(user.getUserPreferences().getNumberOfChildren()),
                Mockito.eq(user.getUserPreferences().getTripDuration()),
                Mockito.eq(680)))
                .thenReturn(providerList);

        Assertions.assertThat(tripPricerService.getTripDeals(user)).isEqualTo(providerList);
    }

}
