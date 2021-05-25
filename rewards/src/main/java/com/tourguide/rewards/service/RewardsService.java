package com.tourguide.rewards.service;

import java.util.UUID;

public interface RewardsService {
    /**
     * Get the reward value of an attraction and a user.
     *
     * @param attractionId the id to identify the attraction
     * @param userId       the id to identify the user
     * @return an int, the reward value. <b>-1</b> is returned if the parameters are wrong.
     */
    int getAttractionRewardPoints(UUID attractionId, UUID userId);
}
