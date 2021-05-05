package com.tourguide.users.service.impl;

import com.tourguide.users.entity.User;
import com.tourguide.users.entity.UserReward;
import com.tourguide.users.properties.TripPricerServiceProperties;
import com.tourguide.users.service.TripPricerService;
import com.tourguide.users.util.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.List;

@Service
@Slf4j
public class TripPricerServiceImpl implements TripPricerService {

    private final TripPricerServiceProperties tripPricerServiceProperties;

    private final TripPricer tripPricer;

    public TripPricerServiceImpl(TripPricerServiceProperties tripPricerServiceProperties, TripPricer tripPricer) {
        this.tripPricerServiceProperties = tripPricerServiceProperties;
        this.tripPricer = tripPricer;
    }

    @Override
    public List<Provider> getTripDeals(User user) {
        int cumulatativeRewardPoints = CollectionUtil.notNullOrEmpty(user.getUserRewards()).stream().mapToInt(UserReward::getRewardPoints).sum();
        log.info("TripPricer service success: getTripDeals");
        return tripPricer.getPrice(
                tripPricerServiceProperties.getTripPricerApiKey(),
                user.getUserId(),
                user.getUserPreferences().getNumberOfAdults(),
                user.getUserPreferences().getNumberOfChildren(),
                user.getUserPreferences().getTripDuration(),
                cumulatativeRewardPoints);
    }
}
