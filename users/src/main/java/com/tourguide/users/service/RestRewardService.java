package com.tourguide.users.service;

import java.util.UUID;

public interface RestRewardService {

    /**
     * The value is get from the Reward Module by http request.
     * Get the reward value of an attraction and a user.
     *
     * @param attractionId the id to identify the attraction
     * @param userId       the id to identify the user
     * @return an int, the reward value. <b>-1</b> is returned if the request response is wrong.
     */

    int getAttractionRewardPoint(UUID attractionId, UUID userId);
}
