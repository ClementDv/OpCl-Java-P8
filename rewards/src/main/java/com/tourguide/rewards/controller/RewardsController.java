package com.tourguide.rewards.controller;

import com.tourguide.rewards.service.RewardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Tag(name = "RewardsController", description = "Controller used to communicate with user module to get Rewards information")
public class RewardsController {

    private final RewardsService rewardsService;

    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @GetMapping("/getAttractionRewardPoints")
    @Operation(description = "Get the rewards point you earn from an attraction")
    public int getRewardPoints(
           @Parameter(name = "attractionId", description = "The id of the attraction") @RequestParam UUID attractionId,
           @Parameter(name = "userId", description = "The id of the user") @RequestParam UUID userId
    ) {
        return rewardsService.getAttractionRewardPoints(attractionId, userId);
    }

}
