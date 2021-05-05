package com.tourguide.rewards.service.impl;

import com.tourguide.rewards.service.RewardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.UUID;

@Service
@Slf4j
public class RewardsServiceImpl implements RewardsService {

    private final RewardCentral rewardCentral;

    public RewardsServiceImpl(RewardCentral rewardCentral) {
        this.rewardCentral = rewardCentral;
    }

    @Override
    public int getAttractionRewardPoints(UUID attractionId, UUID userId) {
        if (attractionId != null && userId != null) {
            log.info("Reward service success : get attraction reward points for attractionId {} and userId {}",
                    attractionId, userId);
            return rewardCentral.getAttractionRewardPoints(attractionId, userId);
        }
        log.warn("Reward service warning : get attraction reward points for attractionId {} and userId {}",
                attractionId, userId);
        return -1;
    }
}
