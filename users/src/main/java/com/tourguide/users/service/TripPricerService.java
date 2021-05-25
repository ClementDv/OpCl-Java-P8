package com.tourguide.users.service;

import com.tourguide.users.entity.User;
import tripPricer.Provider;

import java.util.List;

public interface TripPricerService {
    /**
     * Get the list about all trip information of a user
     *
     * @param user the User to get the values
     * @return a list of Provider(id, name, price)
     */
    List<Provider> getTripDeals(User user);
}
