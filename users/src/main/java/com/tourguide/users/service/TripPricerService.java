package com.tourguide.users.service;

import com.tourguide.users.entity.User;
import tripPricer.Provider;

import java.util.List;

public interface TripPricerService {
    List<Provider> getTripDeals(User user);
}
