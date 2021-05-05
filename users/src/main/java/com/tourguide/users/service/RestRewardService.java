package com.tourguide.users.service;

import java.util.UUID;

public interface RestRewardService {
    int getAttractionRewardPoint(UUID attractionId, UUID userId);
}
