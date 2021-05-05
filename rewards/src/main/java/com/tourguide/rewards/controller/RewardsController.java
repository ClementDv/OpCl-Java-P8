package com.tourguide.rewards.controller;

import com.tourguide.rewards.service.RewardsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RewardsController {

    private final RewardsService rewardsService;

    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @GetMapping("/getAttractionRewardPoints")
    public int getRewardPoints(
            @RequestParam UUID attractionId,
            @RequestParam UUID userId
    ) {
        return rewardsService.getAttractionRewardPoints(attractionId, userId);
    }

}
